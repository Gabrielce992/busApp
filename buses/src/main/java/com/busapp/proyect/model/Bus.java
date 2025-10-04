package com.busapp.proyect.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroBus;
    private String placa;
    private OffsetDateTime fechaCreacion = OffsetDateTime.now();
    private String caracteristicas;

    /**
     * IMPORTANTE: ManyToOne en LAZY para evitar joins automáticos y permitir
     * control explícito de cargas (EntityGraph, DTO o join fetch).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    private Brand marca;

    @Column(length = 10)
    private String estado; // "ACTIVO" o "INACTIVO"
}
