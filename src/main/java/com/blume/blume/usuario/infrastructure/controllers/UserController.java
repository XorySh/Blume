package com.blume.blume.usuario.infrastructure.controllers;

import com.blume.blume.usuario.infrastructure.dto.UsuarioDTO;
import com.blume.blume.usuario.infrastructure.services.UserMapperService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/users")
@Slf4j
public class UserController {

    private final UserMapperService userMapperService;

    public UserController(UserMapperService userMapperService) {
        this.userMapperService = userMapperService;
    }

    /**
     * Guardar un Usuario
     */
    @PostMapping
    public UsuarioDTO createUser(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return userMapperService.createUserDTO(usuarioDTO);
    }

    /**
     * Obtener un Usuario por Id
     */
    @GetMapping("/{userId}")
    public UsuarioDTO getUserById(@PathVariable Long userId){
        return userMapperService.getUserDTO(userId);
    }

    /**
     * Obtener todos los usuarios
     */
    @GetMapping
    public List<UsuarioDTO> getAllUsers() {
        return userMapperService.getAllUsersDTO();
    }

    /**
     * Desactivar un usuario
     */
    @PutMapping("/delete/{userId}")
    public Boolean deactivateUserById(@PathVariable Long userId) {
        return userMapperService.deactivateUser(userId);
    }

    /**
     * Actualizar campos de un usuario
     */
    @PatchMapping("/{userId}")
    public UsuarioDTO updateUser(@PathVariable Long userId,
                                 @RequestBody UsuarioDTO usuarioDTO) {

        return userMapperService.updateUserDTO(userId, usuarioDTO);
    }


}
