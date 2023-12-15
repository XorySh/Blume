package com.blume.blume.auth.services;

import com.blume.blume.auth.responses.AuthenticationResponse;
import com.blume.blume.common.exceptions.ResourceNotFoundException;
import com.blume.blume.jwt.services.JwtService;
import com.blume.blume.jwt.token.RefreshToken;
import com.blume.blume.jwt.token.TokenRepository;
import com.blume.blume.jwt.token.TokenType;
import com.blume.blume.usuario.application.services.UserService;
import com.blume.blume.usuario.domain.entities.Usuario;
import com.blume.blume.usuario.domain.interfaces.Role;
import com.blume.blume.usuario.infrastructure.adapters.UserCrudAdapter;
import com.blume.blume.usuario.infrastructure.dto.LoginDTO;
import com.blume.blume.usuario.infrastructure.dto.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    private final AuthenticationManager authenticationManager;

    private final UserCrudAdapter userCrudAdapter;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    private static final String USER_NOT_CREATED = "No se pudo crear el usuario";




    public AuthenticationResponse register(@Valid RegisterDTO usuarioDTO, Role role) {

        try {

            usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

            // Creamos la entidad
            Usuario usuario = new Usuario();
            // Convertimos el DTO que recibimos a entidad para guardar sus propiedades
            modelMapper.map(usuarioDTO, usuario);

            // Asignamos el campo 'activo' y 'fechaRegistro'
            usuario.setActivo(true);
            usuario.setFechaRegistro(new Date());
            usuario.setRol(role);
            // Creamos el usuario
            Usuario createdUser = this.userService.saveUser(usuario);

            var jwtToken = jwtService.generateToken(createdUser);
            var refreshToken = jwtService.generateRefreshToken(createdUser);
            saveUserToken(createdUser, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new ResourceNotFoundException(USER_NOT_CREATED);
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
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        var token = RefreshToken.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Usuario usuario) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(usuario.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null){
            var user = this.userCrudAdapter.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
