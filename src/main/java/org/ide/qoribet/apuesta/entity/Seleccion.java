package org.ide.qoribet.apuesta.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.common.enums.seleccion.EstadoSeleccion;
import org.ide.qoribet.mercado.entity.Opcion;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "selecciones")
public class Seleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apuesta_id", nullable = false)
    private Apuesta apuesta;

    @ManyToOne
    @JoinColumn(name = "opcion_id", nullable = false)
    private Opcion opcion;

    @Column(nullable = false)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSeleccion estado;
}