package com.br.brenoryan.api.vagas.model.exceptions;

import com.br.brenoryan.api.vagas.model.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity threat400(Exception exception){
        ExceptionResponse exceptionDTO = new ExceptionResponse(
                exception.getMessage(), 400);
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
}
