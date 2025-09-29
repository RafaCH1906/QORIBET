package org.ide.qoribet.partido.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "equipos")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String nombre;

    @NotNull
    @Column(nullable = false)
    private String pais;

    @NotNull
    @Column(nullable = false)
    private String ciudad;

    @Column
    private Integer fundacion;

    @Column(length = 10)
    private String abreviatura;

    @Column
    private String escudo;



}
