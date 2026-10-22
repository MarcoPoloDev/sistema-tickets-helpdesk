package com.tickets.tecnicoservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Integer idTecnico;
    @Column(name = "id_usuario", nullable = false, unique = true)
    private Integer idUsuario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Disponibilidad disponibilidad = Disponibilidad.DISPONIBLE;
    @Column(nullable = false)
    private boolean estado = true;

    public Integer getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Integer v) {
        idTecnico = v;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer v) {
        idUsuario = v;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad v) {
        especialidad = v;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad v) {
        disponibilidad = v;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean v) {
        estado = v;
    }
}
