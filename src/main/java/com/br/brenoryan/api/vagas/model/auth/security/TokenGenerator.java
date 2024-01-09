package com.br.brenoryan.api.vagas.model.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import com.br.brenoryan.api.vagas.model.response.TokenResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenGenerator {

    private String secretKey = "54cr37";

    private Long expireLength = 3600000L; // 1h

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenResponse gerarToken(Usuario usuario) {

        List<String> roles = usuario.getPerfis().stream()
                .map(u -> u.getRoleName().toString()).toList();

        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);
        var accessToken = getAccessToken(usuario.getUsername(), roles, now, validity);
        var refreshToken = getRefreshToken(usuario.getUsername(), roles, now);

        return new TokenResponse(usuario.getUsername(), true, now, validity, accessToken, refreshToken);
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath().build().toUriString()
                + "/api-vagas";
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    private String getRefreshToken(String username, List<String> roles, Date now) {
        Date validityRefreshToken = new Date(now.getTime() + (expireLength * 3));
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validityRefreshToken)
                .withSubject(username)
                .sign(algorithm)
                .strip();
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey.getBytes()))
                .build().verify(token).getSubject();
    }

}
