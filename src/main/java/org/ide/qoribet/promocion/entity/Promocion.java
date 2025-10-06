package org.ide.qoribet.promocion.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ide.qoribet.common.enums.TipoPromocion.TipoPromocion;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promocion", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@Builder
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPromocion tipo;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2, name = "monto_minimo")
    private BigDecimal montoMinimo;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2, name = "beneficio_maximo")
    private BigDecimal beneficioMaximo;

    @NotNull
    @Column(nullable = false, name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull
    @Column(nullable = false, name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @NotNull
    @Column(nullable = false, name = "usos_maximos")
    private Integer usosMaximos;

    @NotNull
    @Column(nullable = false, name = "usos_actuales")
    private Integer usosActuales;

    @NotNull
    @Column(nullable = false)
    private Boolean activo;

    @Column(length = 500)
    private String descripcion;
}
