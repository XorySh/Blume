package com.blume.blume.usuario.application.services;

import com.blume.blume.usuario.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Usuario saveUser(Usuario usuario);
    Optional<Usuario> getUserById(Long id);
    List<Usuario> getAllUsers();
    Optional<Usuario> updateUser(Usuario usuario);

}
