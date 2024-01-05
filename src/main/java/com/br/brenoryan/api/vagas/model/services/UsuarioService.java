package com.br.brenoryan.api.vagas.model.services;

import com.br.brenoryan.api.vagas.model.entities.Perfil;
import com.br.brenoryan.api.vagas.model.entities.Usuario;
import com.br.brenoryan.api.vagas.model.repositories.PerfilRepository;
import com.br.brenoryan.api.vagas.model.repositories.UsuarioRepository;
import com.br.brenoryan.api.vagas.model.request.UsuarioRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Não foi encontrado o usúario:" + username));
    }

    @Transactional
    public void criarUsuario(UsuarioRequest request) throws Exception {

        if(request.getListRole().isEmpty()){
            throw new Exception("A lista de perfis não pode ser vazio.");
        }

        List<Perfil> perfis = perfilRepository.findByRoleNameIn(request.getListRole());

        if(perfis.isEmpty()){
            throw new Exception("Perfil inexistente.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setSenha(new BCryptPasswordEncoder().encode(request.getPassword()));
        usuario.setPerfis(perfis);

        repository.save(usuario);
    }
}
