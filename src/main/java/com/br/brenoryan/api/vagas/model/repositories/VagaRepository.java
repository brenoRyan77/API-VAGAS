package com.br.brenoryan.api.vagas.model.repositories;

import com.br.brenoryan.api.vagas.model.entities.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    @Query("FROM Vaga v WHERE v.dataEncerramento IS NULL and v.id = :id")
    Optional<Vaga> buscarVagaAtivaPorid(@Param("id") Long id);

    Page<Vaga> findByDataEncerramentoIsNull(Pageable pageable);
}
