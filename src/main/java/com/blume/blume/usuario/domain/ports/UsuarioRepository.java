package com.blume.blume.usuario.domain.ports;

import java.util.List;
import java.util.Optional;

import com.blume.blume.usuario.domain.entities.Usuario;

public interface UsuarioRepository {

    Usuario createUser(Usuario usuario);
    Optional<Usuario> updateUser(Usuario usuario);
    Optional<Usuario> getUser(Long id);
    List<Usuario> getAllUsers();
    Boolean deactiveUser(Long id);

}
