package org.ide.qoribet.transaccion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CollectionIdMutability;
import org.ide.qoribet.usuario.entity.Usuario;

import java.util.Date;

@Entity
@Table(name = "transacciones")
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String tipo; //"Recarga o Retiro"

    @NotNull
    private Double monto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fecha = new Date();

    @NotNull
    private String estado; //"Completada, Pendiente, Fallida"

    @NotNull
    private String medioPago; //"Tarjeta, yape , plin"

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    //La transaccion esta ligada a una apuesta
    //si este es el caso usamos
    @ManyToOne
    @JoinColumn(name = "apuesta_id")
    private Apuesta apuesta;

}
