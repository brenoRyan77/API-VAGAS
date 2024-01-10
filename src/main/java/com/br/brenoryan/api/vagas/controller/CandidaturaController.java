package com.br.brenoryan.api.vagas.controller;

import com.br.brenoryan.api.vagas.model.request.CandidaturaRequest;
import com.br.brenoryan.api.vagas.model.response.CandidaturaResponse;
import com.br.brenoryan.api.vagas.model.services.CandidaturaService;
import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidaturas")
@CrossOrigin("*")
@Tag(name = "Candidatura Controller", description = "Operações referentes a Candidatos à vagas.")
public class CandidaturaController {

    @Autowired
    private CandidaturaService service;

    @PostMapping
    public ResponseEntity<?> criarCandidatura(@RequestBody CandidaturaRequest request) throws Exception {
        service.criarCandidatura(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/minhas-candidaturas/{login}")
    public ResponseEntity<List<CandidaturaResponse>> listarMinhasCandidaturas(@PathVariable("login") String login){
        return new ResponseEntity<>(service.minhasCandidaturas(login), HttpStatus.OK);
    }
}
