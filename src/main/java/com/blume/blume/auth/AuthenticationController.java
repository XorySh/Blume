package com.blume.blume.auth;

import com.blume.blume.usuario.infrastructure.dto.LoginDTO;
import com.blume.blume.usuario.infrastructure.dto.UsuarioDTO;
import com.blume.blume.usuario.infrastructure.services.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blume/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserMapperService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UsuarioDTO request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDTO request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
