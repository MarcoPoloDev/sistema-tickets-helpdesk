package com.tickets.tecnicoservice.dto;

import jakarta.validation.constraints.*;

public record EspecialidadRequestDto(@NotBlank @Size(max = 100) String nombre, @Size(max = 255) String descripcion) {
}
