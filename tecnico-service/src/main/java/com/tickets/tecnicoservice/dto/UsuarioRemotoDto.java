package com.tickets.tecnicoservice.dto;

public record UsuarioRemotoDto(Integer idUsuario, String nombres, String apellidos, String correo, String telefono,
                               String rol, boolean estado) {
}
