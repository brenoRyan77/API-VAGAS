package com.br.brenoryan.api.vagas.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_candidatura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_candidatura", unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Usuario candidato;
    @ManyToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;
    private LocalDate dataCandidatura;
    private String status;
}
