package com.tickets.tecnicoservice.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProvider jwt) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> res.sendError(401, "Token requerido o invalido"))
                        .accessDeniedHandler((req, res, ex) -> res.sendError(403, "Sin permiso para esta operacion")))
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Uso interno por ticket-service. No es ruta publicada por API Gateway.
                        .requestMatchers(HttpMethod.GET, "/internal/tecnicos/**").authenticated()
                        .requestMatchers("/api/tecnicos/**", "/api/especialidades/**").hasRole("ADMIN")
                        .anyRequest().denyAll());
        http.addFilterBefore(new JwtFilter(jwt), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
