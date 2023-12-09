package com.blume.blume.usuario.infrastructure.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.blume.blume.common.exceptions.ResourceNotFoundException;
import com.blume.blume.usuario.domain.entities.Usuario;
import com.blume.blume.usuario.domain.ports.UsuarioRepository;


import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserAdapter implements UsuarioRepository {

    private final UserCrudAdapter userCrudAdapter;

    
    public UserAdapter(UserCrudAdapter userCrudAdapter) {
        this.userCrudAdapter = userCrudAdapter;
    }

    @Override
    public Usuario createUser(Usuario usuario) {
        return this.userCrudAdapter.save(usuario);    
    }

    @Override
    public Optional<Usuario> updateUser(Usuario usuario) {
        if (userCrudAdapter.existsById(usuario.getId())){
            Usuario updatedUser = userCrudAdapter.save(usuario);
            return Optional.of(updatedUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> getUser(Long id) {
        Usuario usuario = this.userCrudAdapter.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Usuario no encontrado")
        );
        return Optional.of(usuario);
    }
    

    @Override
    public List<Usuario> getAllUsers() {
        return this.userCrudAdapter.findAll();
    }

    @Override
    public Boolean deactiveUser(Long id) {
        if (this.userCrudAdapter.existsById(id)) {
            Optional<Usuario> usuarioOpt = userCrudAdapter.findById(id);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setActivo(false);
                this.userCrudAdapter.save(usuario);
                return true;
            }
        }
        return false;
    }
    
    
}
