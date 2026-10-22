package com.tickets.tecnicoservice.repository;

import com.tickets.tecnicoservice.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
    boolean existsByNombreIgnoreCase(String nombre);
}
