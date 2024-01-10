package com.br.brenoryan.api.vagas.model.response;

import com.br.brenoryan.api.vagas.model.vo.VagaVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidaturaResponse {

    private Long idCandidatura;
    private String status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAplicacao;
    private VagaVo vaga;
}
