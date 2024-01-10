package com.br.brenoryan.api.vagas.model.repositories;

import com.br.brenoryan.api.vagas.model.entities.Candidatura;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByCandidato(Usuario candidato);

    @Query("SELECT c FROM Candidatura c WHERE c.candidato.id = :idUser AND c.vaga.id = :idVaga")
    List<Candidatura> verificarCandidatura(@Param("idUser") Long idUser, @Param("idVaga") Long idVaga);

    Optional<Candidatura> findByIdAndCandidatoId(Long candidaturaId, Long candidatoId);
}
