package com.tickets.tecnicoservice.service;

import com.tickets.tecnicoservice.client.AuthClient;
import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.entity.*;
import com.tickets.tecnicoservice.exception.BusinessException;
import com.tickets.tecnicoservice.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {
    @Mock
    TecnicoRepository repo;
    @Mock
    EspecialidadService especialidades;
    @Mock
    AuthClient auth;
    @InjectMocks
    TecnicoService service;

    @Test
    void registraTecnicoValido() {
        Especialidad e = new Especialidad();
        e.setIdEspecialidad(2);
        e.setNombre("Software");
        when(repo.existsByIdUsuario(9)).thenReturn(false);
        when(auth.obtenerUsuario(9)).thenReturn(GenericResponse.ok("OK", new UsuarioRemotoDto(9, "Prueba", "Tecnico", "t@test.local", "999999999", "TECNICO", true)));
        when(especialidades.find(2)).thenReturn(e);
        when(repo.saveAndFlush(any(Tecnico.class))).thenAnswer(inv -> {
            Tecnico t = inv.getArgument(0);
            t.setIdTecnico(10);
            return t;
        });
        TecnicoResponseDto r = service.crear(new TecnicoRequestDto(9, 2, Disponibilidad.DISPONIBLE));
        assertEquals(10, r.idTecnico());
        assertEquals(9, r.idUsuario());
        assertEquals("Software", r.especialidad());
    }

    @Test
    void impideUsuarioSinRolTecnico() {
        when(auth.obtenerUsuario(9)).thenReturn(GenericResponse.ok("OK", new UsuarioRemotoDto(9, "Prueba", "Usuario", "u@test.local", "999999999", "USUARIO", true)));
        assertThrows(BusinessException.class, () -> service.crear(new TecnicoRequestDto(9, 2, null)));
        verify(repo, never()).saveAndFlush(any());
    }

    @Test
    void impideDuplicado() {
        when(repo.existsByIdUsuario(9)).thenReturn(true);
        assertThrows(BusinessException.class, () -> service.crear(new TecnicoRequestDto(9, 2, null)));
        verifyNoInteractions(auth);
    }
}
