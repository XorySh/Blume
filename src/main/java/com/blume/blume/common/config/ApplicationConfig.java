package com.blume.blume.common.config;

import com.blume.blume.usuario.application.services.UserService;
import com.blume.blume.usuario.application.services.UserServiceImpl;
import com.blume.blume.usuario.domain.ports.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    UserService userBeanService(final UsuarioRepository usuarioRepository){
        return new UserServiceImpl(usuarioRepository);
    }

}
