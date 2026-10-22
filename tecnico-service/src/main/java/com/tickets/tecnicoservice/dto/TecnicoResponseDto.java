package com.tickets.tecnicoservice.dto;

import com.tickets.tecnicoservice.entity.Disponibilidad;

public record TecnicoResponseDto(
        Integer idTecnico,
        Integer idUsuario,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        Integer idEspecialidad,
        String especialidad,
        Disponibilidad disponibilidad,
        boolean estado) {
}
