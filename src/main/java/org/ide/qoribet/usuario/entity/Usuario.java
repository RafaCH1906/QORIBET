package org.ide.qoribet.usuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ide.qoribet.common.enums.usuario.EstadoUsuario;
import org.ide.qoribet.common.enums.usuario.Rol;

import java.time.LocalDateTime;

@Table(name = "usuarios")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;


    @NotNull
    @Column(nullable = false, unique = true)
    private String correo;

    @NotNull
    @Column(nullable = false)
    private String password_hash;

    @NotNull
    @Column(nullable = false, name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado;

}
