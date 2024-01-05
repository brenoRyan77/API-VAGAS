package com.br.brenoryan.api.vagas.controller;

import com.br.brenoryan.api.vagas.model.auth.security.TokenGenerator;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import com.br.brenoryan.api.vagas.model.request.UsuarioLogin;
import com.br.brenoryan.api.vagas.model.request.UsuarioRequest;
import com.br.brenoryan.api.vagas.model.response.TokenResponse;
import com.br.brenoryan.api.vagas.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("/criar-usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRequest request) throws Exception {
        usuarioService.criarUsuario(request );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UsuarioLogin login){

        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(login.username(), login.password());

        Authentication authentication =  this.authenticationManager.authenticate(userToken);

        var usauario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(tokenGenerator.gerarToken(usauario));
    }
}
