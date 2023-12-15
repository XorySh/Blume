package com.blume.blume.auth.controllers;

import com.blume.blume.auth.responses.AuthenticationResponse;
import com.blume.blume.auth.services.AuthenticationService;
import com.blume.blume.usuario.domain.interfaces.Role;
import com.blume.blume.usuario.infrastructure.dto.LoginDTO;
import com.blume.blume.usuario.infrastructure.dto.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/blume/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private static final Role role = Role.USER;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDTO request
    ) {
        return ResponseEntity.ok(service.register(request, role));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDTO request
    ) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
