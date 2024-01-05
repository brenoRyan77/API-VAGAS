package com.br.brenoryan.api.vagas.model.request;

import com.br.brenoryan.api.vagas.model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private String username;
    private String password;
    private List<RoleName> listRole;
}
