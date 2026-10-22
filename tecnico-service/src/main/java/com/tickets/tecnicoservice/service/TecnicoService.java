package com.tickets.tecnicoservice.service;

import com.tickets.tecnicoservice.client.AuthClient;
import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.entity.*;
import com.tickets.tecnicoservice.exception.*;
import com.tickets.tecnicoservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TecnicoService {
    private final TecnicoRepository repo;
    private final EspecialidadService especialidades;
    private final AuthClient auth;

    public TecnicoService(TecnicoRepository repo, EspecialidadService especialidades, AuthClient auth) {
        this.repo = repo;
        this.especialidades = especialidades;
        this.auth = auth;
    }

    @Transactional(readOnly = true)
    public List<TecnicoResponseDto> listar() {
        return repo.findAll().stream().map(this::map).toList();
    }

    @Transactional(readOnly = true)
    public List<TecnicoResponseDto> activos() {
        return repo.findAll().stream().filter(Tecnico::isEstado).map(this::map).toList();
    }

    @Transactional(readOnly = true)
    public TecnicoResponseDto obtener(Integer id) {
        return map(find(id));
    }

    @Transactional(readOnly = true)
    public TecnicoResponseDto porUsuario(Integer idUsuario) {
        return map(repo.findByIdUsuario(idUsuario).orElseThrow(() -> new NotFoundException("Tecnico no registrado para el usuario: " + idUsuario)));
    }

    @Transactional(readOnly = true)
    public List<TecnicoResponseDto> disponibles() {
        return repo.findByEstadoTrueAndDisponibilidad(Disponibilidad.DISPONIBLE).stream().map(this::map).toList();
    }

    @Transactional
    public TecnicoResponseDto crear(TecnicoRequestDto dto) {
        if (repo.existsByIdUsuario(dto.idUsuario()))
            throw new BusinessException("El usuario ya esta registrado como tecnico");
        validarUsuarioTecnico(dto.idUsuario());
        Especialidad esp = especialidadActiva(dto.idEspecialidad());
        Tecnico t = new Tecnico();
        t.setIdUsuario(dto.idUsuario());
        t.setEspecialidad(esp);
        if (dto.disponibilidad() != null) t.setDisponibilidad(dto.disponibilidad());
        return map(repo.saveAndFlush(t));
    }

    @Transactional
    public TecnicoResponseDto actualizar(Integer id, TecnicoUpdateDto dto) {
        Tecnico t = find(id);
        t.setEspecialidad(especialidadActiva(dto.idEspecialidad()));
        if (dto.disponibilidad() != null) t.setDisponibilidad(dto.disponibilidad());
        if (dto.estado() != null) t.setEstado(dto.estado());
        return map(repo.save(t));
    }

    @Transactional
    public TecnicoResponseDto disponibilidad(Integer id, Disponibilidad disponibilidad) {
        Tecnico t = find(id);
        if (!t.isEstado()) throw new BusinessException("No se puede cambiar la disponibilidad de un tecnico inactivo");
        t.setDisponibilidad(disponibilidad);
        return map(repo.save(t));
    }

    @Transactional
    public void desactivar(Integer id) {
        Tecnico t = find(id);
        t.setEstado(false);
        repo.save(t);
    }

    private Tecnico find(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Tecnico no encontrado: " + id));
    }

    private Especialidad especialidadActiva(Integer id) {
        Especialidad e = especialidades.find(id);
        if (!e.isEstado()) throw new BusinessException("Especialidad inactiva");
        return e;
    }

    private UsuarioRemotoDto usuario(Integer idUsuario) {
        GenericResponse<UsuarioRemotoDto> response = auth.obtenerUsuario(idUsuario);
        UsuarioRemotoDto u = response == null ? null : response.body();
        if (u == null || !Integer.valueOf(idUsuario).equals(u.idUsuario()))
            throw new BusinessException("El usuario indicado no existe");
        return u;
    }

    private void validarUsuarioTecnico(Integer idUsuario) {
        UsuarioRemotoDto u = usuario(idUsuario);
        if (!u.estado()) throw new BusinessException("El usuario esta inactivo");
        if (!"TECNICO".equalsIgnoreCase(u.rol())) throw new BusinessException("El usuario debe tener rol TECNICO");
    }

    private TecnicoResponseDto map(Tecnico t) {
        UsuarioRemotoDto u = usuario(t.getIdUsuario());
        return new TecnicoResponseDto(t.getIdTecnico(), t.getIdUsuario(), u.nombres(), u.apellidos(), u.correo(), u.telefono(), t.getEspecialidad().getIdEspecialidad(), t.getEspecialidad().getNombre(), t.getDisponibilidad(), t.isEstado());
    }
}
