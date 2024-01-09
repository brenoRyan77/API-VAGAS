package com.br.brenoryan.api.vagas.controller;

import com.br.brenoryan.api.vagas.model.request.VagaRequest;
import com.br.brenoryan.api.vagas.model.services.VagaService;
import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
@CrossOrigin("*")
@Tag(name = "Vaga Controller", description = "Operações referentes à Vagas.")
public class VagaController {

    @Autowired
    private VagaService service;

    @PostMapping
    public ResponseEntity<?> criarVaga(@RequestBody @Valid VagaRequest request) {
        service.criarVaga(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarVagaPorId(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(service.buscarVagaPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVaga(@PathVariable("id") Long id ,@RequestBody @Valid VagaRequest request) throws Exception {
        service.atualizarVaga(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<VagaVo>> listarVagas(){
        List<VagaVo> vagas = service.listarVagas();
        return ResponseEntity.ok(vagas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> encerrarVaga(@PathVariable("id") Long id) throws Exception {
        service.encerrarVaga(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
