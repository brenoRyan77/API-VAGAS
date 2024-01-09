package com.br.brenoryan.api.vagas.model.request;

import com.br.brenoryan.api.vagas.model.enums.ModalidadeVaga;
import com.br.brenoryan.api.vagas.model.enums.TipoVaga;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VagaRequest {
    @NotNull(message = "O título da vaga é obrigatório.")
    private String titulo;
    @NotNull(message = "A descrição da vaga é obrigatório.")
    private String descricao;
    private ModalidadeVaga modalidadeVaga;
    private TipoVaga tipoVaga;
    private String nivel;
    private String escolaridade;
}
