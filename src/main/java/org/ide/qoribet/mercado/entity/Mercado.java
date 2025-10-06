package org.ide.qoribet.mercado.entity;
import org.ide.qoribet.common.enums.mercado.TipoMercado;
import org.ide.qoribet.partido.entity.Partido;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mercados")
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMercado tipo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private boolean activo;
}