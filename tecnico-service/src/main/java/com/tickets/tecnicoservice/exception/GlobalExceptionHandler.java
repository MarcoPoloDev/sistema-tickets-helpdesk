package com.tickets.tecnicoservice.exception;

import com.tickets.tecnicoservice.dto.GenericResponse;
import feign.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> missing(NotFoundException e) {
        return respond(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({BusinessException.class, DataIntegrityViolationException.class})
    ResponseEntity<?> business(Exception e) {
        return respond(HttpStatus.CONFLICT, e instanceof BusinessException ? e.getMessage() : "Duplicado o referencia incorrecta");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    ResponseEntity<?> validation(Exception e) {
        return respond(HttpStatus.BAD_REQUEST, "Datos invalidos: verificar campos, IDs y disponibilidad");
    }

    @ExceptionHandler(FeignException.NotFound.class)
    ResponseEntity<?> remoteMissing(Exception e) {
        return respond(HttpStatus.UNPROCESSABLE_ENTITY, "Usuario no encontrado en auth-service");
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    ResponseEntity<?> remoteUnauthorized(Exception e) {
        return respond(HttpStatus.BAD_GATEWAY, "auth-service rechazo las credenciales delegadas");
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    ResponseEntity<?> remoteForbidden(Exception e) {
        return respond(HttpStatus.BAD_GATEWAY, "auth-service no permite consultar el usuario");
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<?> remoteDown(Exception e) {
        return respond(HttpStatus.BAD_GATEWAY, "Error al consultar auth-service");
    }

    @ExceptionHandler(feign.RetryableException.class)
    ResponseEntity<?> remoteOffline(Exception e) {
        return respond(HttpStatus.SERVICE_UNAVAILABLE, "auth-service no disponible");
    }

    private ResponseEntity<?> respond(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(GenericResponse.error(msg));
    }
}
