package com.blume.blume.usuario.infrastructure.controllers;

import com.blume.blume.usuario.infrastructure.dto.UsuarioDTO;
import com.blume.blume.usuario.infrastructure.services.UserMapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blume")
@Slf4j
public class UserController {

    private final UserMapperService userMapperService;

    public UserController(UserMapperService userMapperService) {
        this.userMapperService = userMapperService;
    }


    /**
     * Obtener un Usuario por Id
     */
    @GetMapping("/users/{userId}")
    public UsuarioDTO getUserById(@PathVariable Long userId){
        return userMapperService.getUserDTO(userId);
    }

    /**
     * Obtener todos los usuarios
     */
    @GetMapping("/users/all")
    public List<UsuarioDTO> getAllUsers() {
        return userMapperService.getAllUsersDTO();
    }

    /**
     * Desactivar un usuario
     */
    @PutMapping("/users/delete/{userId}")
    public Boolean deactivateUserById(@PathVariable Long userId) {
        return userMapperService.deactivateUser(userId);
    }

    /**
     * Actualizar campos de un usuario
     */
    @PatchMapping("/users/{userId}")
    public UsuarioDTO updateUser(@PathVariable Long userId,
                                 @RequestBody UsuarioDTO usuarioDTO) {

        return userMapperService.updateUserDTO(userId, usuarioDTO);
    }




}
