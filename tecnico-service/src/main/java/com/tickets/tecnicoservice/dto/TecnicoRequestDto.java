package com.tickets.tecnicoservice.dto;

import com.tickets.tecnicoservice.entity.Disponibilidad;
import jakarta.validation.constraints.NotNull;

public record TecnicoRequestDto(
        @NotNull(message = "idUsuario obligatorio") Integer idUsuario,
        @NotNull(message = "idEspecialidad obligatorio") Integer idEspecialidad,
        Disponibilidad disponibilidad) {
}
