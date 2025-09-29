package org.ide.qoribet.promocion.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.usuario.entity.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promociones_usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PromocionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promocion_id", nullable = false)
    private Promocion promocion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Column(nullable = false)
    private Boolean usada;

    @Column(precision = 12, scale = 2)
    private BigDecimal montoAplicado;

    @Column(precision = 12, scale = 2)
    private BigDecimal beneficioOtorgado;

    @Column
    private LocalDateTime fechaAplicacion;

    @Column
    private Boolean utilizado;
}