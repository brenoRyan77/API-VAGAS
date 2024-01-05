package com.br.brenoryan.api.vagas.model.services;

import com.br.brenoryan.api.vagas.model.entities.Vaga;
import com.br.brenoryan.api.vagas.model.repositories.VagaRepository;
import com.br.brenoryan.api.vagas.model.request.VagaRequest;
import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository repository;

    public void criarVaga(VagaRequest request){

        Vaga novaVaga = new Vaga();
        novaVaga.setTitulo(request.getTitulo());
        novaVaga.setDescricao(request.getDescricao());
        novaVaga.setModalidadeVaga(request.getModalidadeVaga());
        novaVaga.setTipoVaga(request.getTipoVaga());
        repository.save(novaVaga);
    }

    public VagaVo buscarVagaPorId(Long idVaga) throws Exception {
        Optional<Vaga> vaga = repository.buscarVagaAtivaPorid(idVaga);
        if(vaga.isEmpty()){
            throw new Exception("Vaga não encontrada.");
        }

        return new VagaVo(vaga.get());
    }

    public List<VagaVo> listarVagas(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaga> vagas = repository.findByDataEncerramentoIsNull(pageable);
        return vagas.map(VagaVo::new).toList();
    }

    @Transactional
    public void encerrarVaga(Long id) throws Exception {
        Optional<Vaga> vaga = repository.buscarVagaAtivaPorid(id);
        if(vaga.isEmpty()){
            throw new Exception("Vaga nao encontrada.");
        }

        Vaga encerrarVaga = vaga.get();
        encerrarVaga.setDataEncerramento(LocalDate.now());
    }

    public void atualizarVaga(Long id, VagaRequest request) throws Exception {
        Optional<Vaga> vaga = repository.buscarVagaAtivaPorid(id);
        if(vaga.isEmpty()){
            throw new Exception("Vaga nao encontrada.");
        }
        repository.save(preencherAtributos(vaga.get(), request));
    }

    public Vaga preencherAtributos(Vaga vaga, VagaRequest request){
        vaga.setTitulo(request.getTitulo());
        vaga.setDescricao(request.getDescricao());
        vaga.setModalidadeVaga(request.getModalidadeVaga());
        vaga.setTipoVaga(request.getTipoVaga());
        return vaga;
    }
}
