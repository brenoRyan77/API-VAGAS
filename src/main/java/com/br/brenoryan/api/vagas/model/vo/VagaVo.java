package com.br.brenoryan.api.vagas.model.vo;

import com.br.brenoryan.api.vagas.model.entities.Vaga;
import com.br.brenoryan.api.vagas.model.enums.ModalidadeVaga;
import com.br.brenoryan.api.vagas.model.enums.TipoVaga;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VagaVo {

    private Long id;
    @NotNull(message = "O título da vaga é obrigatório.")
    private String titulo;
    @NotNull(message = "A descrição da vaga é obrigatório.")
    private String descricao;
    private ModalidadeVaga modalidadeVaga;
    private TipoVaga tipoVaga;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEncerramento;

    public VagaVo(Vaga vaga){
        if(vaga == null) return;
        this.id = vaga.getId();
        this.titulo = vaga.getTitulo();
        this.descricao = vaga.getDescricao();
        this.modalidadeVaga = vaga.getModalidadeVaga();
        this.tipoVaga = vaga.getTipoVaga();
        this.dataPublicacao = vaga.getDataPublicacao();
        this.dataEncerramento = vaga.getDataEncerramento();
    }
}
