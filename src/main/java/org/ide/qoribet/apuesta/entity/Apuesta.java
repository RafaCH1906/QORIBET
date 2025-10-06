package org.ide.qoribet.apuesta.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.mercado.entity.Opcion;
import org.ide.qoribet.common.enums.apuesta.EstadoApuesta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "apuestas")
public class Apuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "opcion_id", nullable = false)
    private Opcion opcion;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoApuesta estado;
}