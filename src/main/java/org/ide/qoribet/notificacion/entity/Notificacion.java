package org.ide.qoribet.notificacion.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ide.qoribet.usuario.entity.Usuario;

import java.util.Date;

@Entity
@Table(name = "notificaciones")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String mensaje;

    @NotBlank
    private String tipo; //"Apuesta, bono , sistema, etc"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fecha = new Date();

    private boolean leida = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
