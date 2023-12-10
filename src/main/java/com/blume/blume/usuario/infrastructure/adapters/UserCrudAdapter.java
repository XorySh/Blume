package com.blume.blume.usuario.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blume.blume.usuario.domain.entities.Usuario;

import java.util.Optional;

@Repository
public interface UserCrudAdapter extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}