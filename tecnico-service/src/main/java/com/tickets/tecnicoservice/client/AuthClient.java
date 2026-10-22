package com.tickets.tecnicoservice.client;

import com.tickets.tecnicoservice.config.FeignAuthConfig;
import com.tickets.tecnicoservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", configuration = FeignAuthConfig.class)
public interface AuthClient {
    @GetMapping("/internal/usuarios/{id}")
    GenericResponse<UsuarioRemotoDto> obtenerUsuario(@PathVariable("id") Integer id);
}
