package com.blume.blume.usuario.infrastructure.services;


import com.blume.blume.auth.AuthenticationResponse;
import com.blume.blume.common.exceptions.ResourceNotFoundException;
import com.blume.blume.common.jwt.JwtService;
import com.blume.blume.usuario.application.services.UserService;
import com.blume.blume.usuario.domain.entities.Usuario;
import com.blume.blume.usuario.domain.interfaces.Role;
import com.blume.blume.usuario.infrastructure.adapters.UserCrudAdapter;
import com.blume.blume.usuario.infrastructure.dto.LoginDTO;
import com.blume.blume.usuario.infrastructure.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMapperService {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    private static final String USER_NOT_FOUND = "No se pudo encontrar el usuario";

    private final AuthenticationManager authenticationManager;

    private final UserCrudAdapter userCrudAdapter;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO getUserDTO(Long id) {

        if (id != null){
            // Se intenta obtener un usuario existente basado en el ID proporcionado.
            Optional<Usuario> usuario = this.userService.getUserById(id);

            // Se verifica si el usuario existe en la base de datos.
            if (usuario.isPresent()){
                // Se obtiene el usuario existente de Optional.
                Usuario existingUser = usuario.get();

                // Devolvemos el usuarioDTO ya mapeado
                return modelMapper.map(existingUser, UsuarioDTO.class);
            }
            // Si no encuentra el usuario existente lanza una excepción.
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        // Lanza una excepción si se accede al método pero no busca ningún id
        throw new ResourceNotFoundException("El usuario no existe");
    }

    public List<UsuarioDTO> getAllUsersDTO() {

        try {
            // Se llama al servicio para encontrar todos los usuarios.
            List<Usuario> usuarios = this.userService.getAllUsers();

            // Realizamos el mapping de usuarios a usuarioDTOS
            List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
            for (Usuario usuario : usuarios){
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                modelMapper.map(usuario, usuarioDTO);
                usuarioDTOS.add(usuarioDTO);
            }
            // Devolvemos todos los usuarios
            return usuarioDTOS;
        }catch (Exception e){
            // Si no encuentra usuarios existentes lanza una excepción.
            throw new ResourceNotFoundException("No se pudo encontrar usuarios");
        }
    }

    public AuthenticationResponse register(@Valid UsuarioDTO usuarioDTO) {

        try {

            usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

            // Creamos la entidad
            Usuario usuario = new Usuario();
            // Convertimos el DTO que recibimos a entidad para guardar sus propiedades
            modelMapper.map(usuarioDTO, usuario);

            // Asignamos el campo 'activo' y 'fechaRegistro'
            usuario.setActivo(true);
            usuario.setFechaRegistro(new Date());
            usuario.setRol(Role.USER);



            // Creamos el usuario
            Usuario createdUser = this.userService.saveUser(usuario);

            var jwtToken = jwtService.generateToken(createdUser);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se pudo crear el usuario");
        }

    }

    public AuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userCrudAdapter.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public Boolean deactivateUser(Long id) {
        if (id != null) {
            Optional<Usuario> usuario = this.userService.getUserById(id);

            if (usuario.isPresent()) {
                try{
                    Usuario existingUser = usuario.get();
                    existingUser.setActivo(false);
                    this.userService.saveUser(existingUser);
                    return true;
                }catch (Exception e){
                    throw new ResourceNotFoundException("No se pudo desactivar el usuario");
                }
            }
        }
        // Si no encuentra el usuario existente lanza una excepción.
        throw new ResourceNotFoundException(USER_NOT_FOUND);
    }

    public UsuarioDTO updateUserDTO(Long id, UsuarioDTO usuarioDTO) {

        if (id != null){
            // Se intenta obtener un usuario existente basado en el ID proporcionado.
            Optional<Usuario> usuario = this.userService.getUserById(id);

            // Se verifica si el usuario existe en la base de datos.
            if (usuario.isPresent()){
                // Se obtiene el usuario existente de Optional.
                Usuario existingUser = usuario.get();
                // Se copian las propiedades de userUpdateDTO a existingUser para actualizarlo.
                modelMapper.map(usuarioDTO, existingUser);

                try{
                    // Se llama al servicio para actualizar el usuario.
                    Optional<Usuario> optionalUserDTO = this.userService.updateUser(existingUser);

                    // Si la actualización fue exitosa, se copian las propiedades del usuario a UpdatedUserDTO.
                    if (optionalUserDTO.isPresent()){
                        Usuario updatedUser = optionalUserDTO.get();
                        UsuarioDTO updatedUserDTO = new UsuarioDTO();
                        modelMapper.map(updatedUser, updatedUserDTO);

                        //Se devuelve el updatedUserDTO con los datos actualizados.
                        return updatedUserDTO;
                    }
                }catch (Exception e) {
                    // Si ocurre una excepción durante la actualización, se lanza una ResourceNotFoundException.
                    throw new ResourceNotFoundException("No se pudo actualizar el usuario");
                }
            }

        }
        // Si no encuentra el usuario existente lanza una excepción.
        throw new ResourceNotFoundException(USER_NOT_FOUND);
    }






}
