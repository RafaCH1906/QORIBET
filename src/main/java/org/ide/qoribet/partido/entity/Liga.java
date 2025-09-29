package org.ide.qoribet.partido.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "ligas")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String categoria;

    @Column(length = 10)
    private String abreviatura;

}
