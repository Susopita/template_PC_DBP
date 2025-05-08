package com.example.template_PC.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "nombre_entidad")
public class entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @NotNull
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @NotNull
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotNull
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
