package org.ide.qoribet.saldo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.usuario.entity.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "saldo_digital")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SaldoDigital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "monto_actual", nullable = false, precision = 19, scale = 2)
    private BigDecimal montoActual;

    @Column(name = "ultima_actualizacion", nullable = false)
    private LocalDateTime ultimaActualizacion;
}
