package com.blume.blume.usuario.domain.ports;

import com.blume.blume.usuario.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario createUser(Usuario usuario);
    Optional<Usuario> updateUser(Usuario usuario);
    Optional<Usuario> getUser(Long id);
    List<Usuario> getAllUsers();

}
