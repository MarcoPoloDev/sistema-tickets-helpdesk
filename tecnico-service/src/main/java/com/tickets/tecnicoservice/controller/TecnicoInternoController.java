package com.tickets.tecnicoservice.controller;

import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.service.TecnicoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/tecnicos")
public class TecnicoInternoController {
    private final TecnicoService service;

    public TecnicoInternoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public GenericResponse<TecnicoResponseDto> obtener(@PathVariable Integer id) {
        return GenericResponse.ok("Tecnico encontrado", service.obtener(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public GenericResponse<TecnicoResponseDto> porUsuario(@PathVariable Integer idUsuario) {
        return GenericResponse.ok("Tecnico por usuario", service.porUsuario(idUsuario));
    }
}
