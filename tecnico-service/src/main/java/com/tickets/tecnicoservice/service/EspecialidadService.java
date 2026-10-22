package com.tickets.tecnicoservice.service;

import com.tickets.tecnicoservice.dto.*;
import com.tickets.tecnicoservice.entity.Especialidad;
import com.tickets.tecnicoservice.exception.*;
import com.tickets.tecnicoservice.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EspecialidadService {
    private final EspecialidadRepository repo;

    public EspecialidadService(EspecialidadRepository repo) {
        this.repo = repo;
    }

    public List<EspecialidadResponseDto> listar() {
        return repo.findAll().stream().map(this::map).toList();
    }

    public List<EspecialidadResponseDto> listarActivas() {
        return repo.findAll().stream().filter(Especialidad::isEstado).map(this::map).toList();
    }

    public EspecialidadResponseDto obtener(Integer id) {
        return map(find(id));
    }

    public Especialidad find(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Especialidad no encontrada: " + id));
    }

    @Transactional
    public EspecialidadResponseDto crear(EspecialidadRequestDto dto) {
        if (repo.existsByNombreIgnoreCase(dto.nombre().trim())) throw new BusinessException("Especialidad duplicada");
        Especialidad e = new Especialidad();
        e.setNombre(dto.nombre().trim());
        e.setDescripcion(dto.descripcion());
        return map(repo.save(e));
    }

    @Transactional
    public EspecialidadResponseDto modificar(Integer id, EspecialidadRequestDto dto) {
        Especialidad e = find(id);
        if (!e.getNombre().equalsIgnoreCase(dto.nombre().trim()) && repo.existsByNombreIgnoreCase(dto.nombre().trim()))
            throw new BusinessException("Especialidad duplicada");
        e.setNombre(dto.nombre().trim());
        e.setDescripcion(dto.descripcion());
        return map(repo.save(e));
    }

    @Transactional
    public void desactivar(Integer id) {
        Especialidad e = find(id);
        e.setEstado(false);
        repo.save(e);
    }

    private EspecialidadResponseDto map(Especialidad e) {
        return new EspecialidadResponseDto(e.getIdEspecialidad(), e.getNombre(), e.getDescripcion(), e.isEstado());
    }
}
