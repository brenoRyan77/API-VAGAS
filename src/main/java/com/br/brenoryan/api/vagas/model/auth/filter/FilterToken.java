package com.br.brenoryan.api.vagas.model.auth.filter;

import com.br.brenoryan.api.vagas.model.auth.security.TokenGenerator;
import com.br.brenoryan.api.vagas.model.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    UsuarioService usuarioService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token;

        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            token = authorizationHeader.replace("Bearer ", "");
            var subject = tokenGenerator.getSubject(token);
            var usuario = usuarioService.loadUserByUsername(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                    usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }
}
