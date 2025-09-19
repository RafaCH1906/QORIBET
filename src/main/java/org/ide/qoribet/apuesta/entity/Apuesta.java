package org.ide.qoribet.apuesta.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ide.qoribet.usuario.entity.Usuario;

import java.util.Date;

@Entity
@Table(name = "apuestas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Apuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double cuota;

    @NotNull
    private Double monto;

    @NotNull
    private String estado; //"Ganada, Perdida, Pendiente"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fecha = new Date();

    @NotNull
    private String tipo; //"depotivo o juegoazar"

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "juego_azar_id")
    private JuegoAzar juegoAzar;
}
