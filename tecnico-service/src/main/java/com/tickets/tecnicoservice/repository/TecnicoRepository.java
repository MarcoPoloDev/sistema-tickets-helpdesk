package com.tickets.tecnicoservice.repository;

import com.tickets.tecnicoservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
    boolean existsByIdUsuario(Integer idUsuario);

    Optional<Tecnico> findByIdUsuario(Integer idUsuario);

    List<Tecnico> findByEstadoTrueAndDisponibilidad(Disponibilidad disponibilidad);
}
