package com.blume.blume;

import com.blume.blume.auth.services.AuthenticationService;
import com.blume.blume.usuario.domain.entities.Usuario;
import com.blume.blume.usuario.domain.interfaces.Role;
import com.blume.blume.usuario.infrastructure.adapters.UserCrudAdapter;
import com.blume.blume.usuario.infrastructure.dto.RegisterDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.logging.Logger;

@SpringBootApplication
public class BlumeApplication {

	Logger logger = Logger.getLogger(getClass().getName());
	private final UserCrudAdapter userCrudAdapter;

	public BlumeApplication(UserCrudAdapter userCrudAdapter) {
		this.userCrudAdapter = userCrudAdapter;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlumeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			String administrador = "admin";
			String management = "manager";


			RegisterDTO admin = new RegisterDTO("admin@admin.com","hombre","password"
					,administrador,administrador,administrador,"636363636"
					,"localidad","provincia","pais","direccion");

			RegisterDTO manager = new RegisterDTO("manager@manager.com","hombre","password"
					,management,management,management,"635353535"
					,"localidad","provincia","pais","direccion");

			if (userExists(admin)){
				logger.info("Admin token: " + service.register(admin, Role.ADMIN).getAccessToken());
			}

			if (userExists(manager)){
				logger.info("Manager token: " + service.register(manager, Role.MANAGER).getAccessToken());
			}

		};
	}

	private boolean userExists(RegisterDTO registerDTO) {
		Optional<Usuario> usuarioOptional = userCrudAdapter.findByEmail(registerDTO.getEmail());
        return usuarioOptional.isEmpty();
    }

}
