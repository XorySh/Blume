package com.blume.blume.usuario.infrastructure.controllers;

import com.blume.blume.auth.services.AuthenticationService;
import com.blume.blume.usuario.infrastructure.dto.ChangePasswordDTO;
import com.blume.blume.usuario.infrastructure.dto.UsuarioDTO;
import com.blume.blume.usuario.infrastructure.services.UserMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/blume")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserMapperService userMapperService;
    private final AuthenticationService service;


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

    /**
     * Cambiar contraseña
     */
    @PatchMapping("/users/change_password")
    public ResponseEntity<ChangePasswordDTO> changePassword(
            @RequestBody ChangePasswordDTO request,
            Principal connectedUser
    ){
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }


}
