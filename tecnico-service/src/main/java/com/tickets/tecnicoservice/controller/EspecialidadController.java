package com.tickets.tecnicoservice.controller;

import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {
    private final EspecialidadService service;

    public EspecialidadController(EspecialidadService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse<List<EspecialidadResponseDto>> listar() {
        return GenericResponse.ok("Listado de especialidades", service.listar());
    }

    @GetMapping("/activas")
    public GenericResponse<List<EspecialidadResponseDto>> activas() {
        return GenericResponse.ok("Especialidades activas", service.listarActivas());
    }

    @GetMapping("/{id}")
    public GenericResponse<EspecialidadResponseDto> obtener(@PathVariable Integer id) {
        return GenericResponse.ok("Especialidad encontrada", service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<EspecialidadResponseDto>> crear(@Valid @RequestBody EspecialidadRequestDto dto) {
        return ResponseEntity.status(201).body(GenericResponse.ok("Especialidad creada", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public GenericResponse<EspecialidadResponseDto> modificar(@PathVariable Integer id, @Valid @RequestBody EspecialidadRequestDto dto) {
        return GenericResponse.ok("Especialidad actualizada", service.modificar(id, dto));
    }

    @DeleteMapping("/{id}")
    public GenericResponse<Void> eliminar(@PathVariable Integer id) {
        service.desactivar(id);
        return GenericResponse.ok("Especialidad desactivada", null);
    }
}
