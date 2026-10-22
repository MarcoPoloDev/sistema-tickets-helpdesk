package com.tickets.tecnicoservice.dto;

public record GenericResponse<T>(String tipo, int rpta, String mensaje, T body) {
    public static <T> GenericResponse<T> ok(String mensaje, T data) {
        return new GenericResponse<>("success", 1, mensaje, data);
    }

    public static <T> GenericResponse<T> error(String mensaje) {
        return new GenericResponse<>("error", 0, mensaje, null);
    }
}
