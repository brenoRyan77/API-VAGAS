package com.br.brenoryan.api.vagas.model.entities;

import com.br.brenoryan.api.vagas.model.enums.ModalidadeVaga;
import com.br.brenoryan.api.vagas.model.enums.TipoVaga;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tb_vagas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cod_vaga", unique = true)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(nullable = false, name = "modalidade_vaga")
    @Enumerated(EnumType.STRING)
    private ModalidadeVaga modalidadeVaga;
    @Column(nullable = false, name = "tipo_vaga")
    @Enumerated(EnumType.STRING)
    private TipoVaga tipoVaga;
    @CreationTimestamp
    @Column(name = "dt_publicacao")
    private LocalDate dataPublicacao;
    @Column(name = "dt_encerramento")
    private LocalDate dataEncerramento;
}
