package com.blume.blume.usuario.application.services;

import com.blume.blume.usuario.domain.entities.Usuario;
import com.blume.blume.usuario.domain.ports.UsuarioRepository;

import java.util.List;
import java.util.Optional;

public record UserServiceImpl(UsuarioRepository usuarioRepository) implements UserService {

    @Override
    public Usuario saveUser(Usuario usuario) {
       return this.usuarioRepository.createUser(usuario);
    }

    @Override
    public Optional<Usuario> getUserById(Long id) {
        return this.usuarioRepository.getUser(id);
    }

    @Override
    public List<Usuario> getAllUsers() {
        return this.usuarioRepository.getAllUsers();
    }

    @Override
    public Optional<Usuario> updateUser(Usuario usuario) {
        return this.usuarioRepository.updateUser(usuario);
    }


    
}
