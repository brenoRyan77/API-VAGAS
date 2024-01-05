package com.br.brenoryan.api.vagas.model.repositories;

import com.br.brenoryan.api.vagas.model.entities.Perfil;
import com.br.brenoryan.api.vagas.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    public List<Perfil> findByRoleNameIn(List<RoleName> roles);
    public Perfil findByRoleName(String nome);
}
