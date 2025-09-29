package org.ide.qoribet.saldo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.common.enums.saldo.EstadoOperacion;
import org.ide.qoribet.common.enums.saldo.MetodoPago;
import org.ide.qoribet.common.enums.saldo.TipoOperacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operacion_saldo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OperacionSaldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "saldo_id", nullable = false)
    private SaldoDigital saldoDigital;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOperacion tipo;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @Column(name = "saldo_anterior", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(nullable = false)
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOperacion estadoOperacion;

    @Column(name = "fecha_operacion", nullable = false)
    private LocalDateTime fechaOperacion;

    @Column
    private String obeservacion;

}
