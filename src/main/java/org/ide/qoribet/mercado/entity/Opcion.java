package org.ide.qoribet.mercado.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ide.qoribet.common.enums.mercado.NombreOpcion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "opciones")
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mercado_id", nullable = false)
    private Mercado mercado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NombreOpcion nombre;

    @Column(nullable = false)
    private String descripcion;
}