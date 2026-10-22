package com.tickets.tecnicoservice.controller;

import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.entity.Disponibilidad;
import com.tickets.tecnicoservice.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {
    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse<List<TecnicoResponseDto>> listar() {
        return GenericResponse.ok("Listado de tecnicos", service.listar());
    }

    @GetMapping("/activos")
    public GenericResponse<List<TecnicoResponseDto>> activos() {
        return GenericResponse.ok("Tecnicos activos", service.activos());
    }

    @GetMapping("/disponibles")
    public GenericResponse<List<TecnicoResponseDto>> disponibles() {
        return GenericResponse.ok("Tecnicos disponibles", service.disponibles());
    }

    @GetMapping("/usuario/{idUsuario}")
    public GenericResponse<TecnicoResponseDto> usuario(@PathVariable Integer idUsuario) {
        return GenericResponse.ok("Tecnico por usuario", service.porUsuario(idUsuario));
    }

    @GetMapping("/{id}")
    public GenericResponse<TecnicoResponseDto> obtener(@PathVariable Integer id) {
        return GenericResponse.ok("Tecnico encontrado", service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<TecnicoResponseDto>> crear(@Valid @RequestBody TecnicoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.ok("Tecnico registrado", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public GenericResponse<TecnicoResponseDto> actualizar(@PathVariable Integer id, @Valid @RequestBody TecnicoUpdateDto dto) {
        return GenericResponse.ok("Tecnico actualizado", service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/disponibilidad")
    public GenericResponse<TecnicoResponseDto> disponibilidad(@PathVariable Integer id, @RequestBody Map<String, Disponibilidad> body) {
        if (body.get("disponibilidad") == null)
            throw new com.tickets.tecnicoservice.exception.BusinessException("Indicar disponibilidad");
        return GenericResponse.ok("Disponibilidad actualizada", service.disponibilidad(id, body.get("disponibilidad")));
    }

    @DeleteMapping("/{id}")
    public GenericResponse<Void> desactivar(@PathVariable Integer id) {
        service.desactivar(id);
        return GenericResponse.ok("Tecnico desactivado", null);
    }
}
