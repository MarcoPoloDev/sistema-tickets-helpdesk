package com.tickets.tecnicoservice.dto;

import com.tickets.tecnicoservice.entity.Disponibilidad;
import jakarta.validation.constraints.NotNull;

public record TecnicoUpdateDto(@NotNull Integer idEspecialidad, Disponibilidad disponibilidad, Boolean estado) {
}
