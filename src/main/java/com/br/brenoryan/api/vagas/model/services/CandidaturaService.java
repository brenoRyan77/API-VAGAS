package com.br.brenoryan.api.vagas.model.services;

import com.br.brenoryan.api.vagas.model.entities.Candidatura;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import com.br.brenoryan.api.vagas.model.entities.Vaga;
import com.br.brenoryan.api.vagas.model.repositories.CandidaturaRepository;
import com.br.brenoryan.api.vagas.model.request.CandidaturaRequest;
import com.br.brenoryan.api.vagas.model.response.CandidaturaResponse;
import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CandidaturaService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VagaService vagaService;
    @Autowired
    private CandidaturaRepository repository;

    public void criarCandidatura(CandidaturaRequest request) throws Exception {

        Usuario user = usuarioService.buscarUsuarioLogado(request.login());
        Vaga vaga = vagaService.buscarVagaPorIdEntidade(request.idVaga());

        if(vaga == null || user == null){
            throw new Exception("usuário ou vaga não encontrados.");
        }

        Optional<Candidatura> candidaturaExistente = repository.verificarCandidatura(user.getId(), vaga.getId());

        if(candidaturaExistente.isPresent()){
            throw new IllegalStateException("Você já está concorrendo a essa vaga.");
        }

        Candidatura candidatura = new Candidatura();
        candidatura.setCandidato(user);
        candidatura.setVaga(vaga);
        candidatura.setDataCandidatura(LocalDate.now());
        candidatura.setStatus("Em análise");
        repository.save(candidatura);
    }

    public List<Candidatura> listarCandidaturaPorCandidato(String userLogado) {
        Usuario user = usuarioService.buscarUsuarioLogado(userLogado);
        List<Candidatura> candidaturasDoUsuario = repository.findByCandidato(user);
        return candidaturasDoUsuario != null ? candidaturasDoUsuario : Collections.emptyList();
    }

    public List<CandidaturaResponse> minhasCandidaturas(String username) {

        List<Candidatura> candidaturas = listarCandidaturaPorCandidato(username);

        if(candidaturas.isEmpty()){
            return new ArrayList<>();
        }

        List<CandidaturaResponse> listaCandidaturas = new ArrayList<>();

        for (Candidatura candidatura : candidaturas) {
            CandidaturaResponse candidaturaResponse = new CandidaturaResponse();
            candidaturaResponse.setIdCandidatura(candidatura.getId());
            candidaturaResponse.setStatus(candidatura.getStatus());
            candidaturaResponse.setDataAplicacao(candidatura.getDataCandidatura());
            candidaturaResponse.setVaga(new VagaVo(candidatura.getVaga()));

            listaCandidaturas.add(candidaturaResponse);
        }

        return listaCandidaturas;
    }
}
