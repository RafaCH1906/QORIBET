package org.ide.qoribet.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Table(name = "personas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "primer_nombre")
    private String primerNombre;

    @NotNull
    @Column(nullable = false, name = "segundo_nombre")
    private String segundoNombre;

    @NotNull
    @Column(nullable = false, name = "primer_apellido")
    private  String primerApellido;

    @NotNull
    @Column(nullable = false, name = "segundo_apellido")
    private String segundoApellido;

    @NotNull
    @Column(nullable = false, name = "tipo_documento")
    private String tipo_documento;

    @NotNull
    @Column(nullable = false, unique = true, name = "numero_documento")
    private String numero_documento;

    @NotNull
    @Column(nullable = false, name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(nullable = false)
    private String direccion;

    @NotNull
    @Column(nullable = false, unique = true)
    private String telefono;


}

