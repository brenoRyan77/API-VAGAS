package com.br.brenoryan.api.vagas.model.services;

import com.br.brenoryan.api.vagas.model.entities.Candidatura;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import com.br.brenoryan.api.vagas.model.entities.Vaga;
import com.br.brenoryan.api.vagas.model.repositories.CandidaturaRepository;
import com.br.brenoryan.api.vagas.model.request.CandidaturaRequest;
import com.br.brenoryan.api.vagas.model.response.CandidaturaResponse;
import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

        if(candidaturaExistente.isPresent() && candidaturaExistente.get().getStatus().equalsIgnoreCase("Em análise")){
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

        Map<Long, Candidatura> mapaCandidaturas = new HashMap<>();
        for (Candidatura candidatura : candidaturas) {
            if (!mapaCandidaturas.containsKey(candidatura.getVaga().getId())
                    || candidatura.getStatus().equals("Em análise")) {
                mapaCandidaturas.put(candidatura.getVaga().getId(), candidatura);
            }
        }

        List<CandidaturaResponse> listaCandidaturas = new ArrayList<>();
        for (Candidatura candidatura : mapaCandidaturas.values()) {
            CandidaturaResponse candidaturaResponse = new CandidaturaResponse();
            candidaturaResponse.setIdCandidatura(candidatura.getId());
            candidaturaResponse.setDataAplicacao(candidatura.getDataCandidatura());

            if (candidatura.getVaga().getDataEncerramento() != null) {
                candidaturaResponse.setStatus("Vaga encerrada");
            } else {
                candidaturaResponse.setStatus(candidatura.getStatus());
            }

            candidaturaResponse.setVaga(new VagaVo(candidatura.getVaga()));
            listaCandidaturas.add(candidaturaResponse);
        }

        return listaCandidaturas;
    }

    @Transactional
    public void desistirCandidatura(String login, Long idCandidatura) throws Exception {
        Usuario user = usuarioService.buscarUsuarioLogado(login);
        Optional<Candidatura> candidatura = repository.findByIdAndCandidatoId(idCandidatura, user.getId());

        if(candidatura.isEmpty()){
            throw new Exception("Candidatura não encontrada.");
        }

        Candidatura desistirCand = candidatura.get();
        desistirCand.setStatus("Desistência");
        repository.save(desistirCand);
    }
}
