package com.blume.blume.common.config;

import com.blume.blume.jwt.components.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.blume.blume.usuario.domain.interfaces.Permission.*;
import static com.blume.blume.usuario.domain.interfaces.Role.ADMIN;
import static com.blume.blume.usuario.domain.interfaces.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/blume/auth/**",
            "/blume/users/**",
            "/blume/products/all",
            "/blume/products/{productId}",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private static final String MANAGEMENT_URL = "/blume/management/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(auth ->
                       auth.requestMatchers(WHITE_LIST_URL)
                               .permitAll()
                               .requestMatchers(MANAGEMENT_URL)
                                    .hasAnyRole(ADMIN.name(), MANAGER.name())
                               .requestMatchers(HttpMethod.GET, MANAGEMENT_URL)
                                    .hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                               .requestMatchers(HttpMethod.POST, MANAGEMENT_URL)
                                    .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                               .requestMatchers(HttpMethod.PATCH, MANAGEMENT_URL)
                                    .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                               .anyRequest()
                               .authenticated()
               )
               .cors(AbstractHttpConfigurer::disable)
               .formLogin(Customizer.withDefaults())
               .sessionManagement( sessionManagement ->
                       sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .logout(logout ->
                       logout.logoutUrl("blume/auth/logout")
                               .addLogoutHandler(logoutHandler)
                               .logoutSuccessHandler((
                                       (request, response, authentication) -> SecurityContextHolder.clearContext())
                               )
               )
               .build();
    }



}
