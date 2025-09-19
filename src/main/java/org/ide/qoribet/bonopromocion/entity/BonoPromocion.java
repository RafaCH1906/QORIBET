package org.ide.qoribet.bonopromocion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ide.qoribet.usuario.entity.Usuario;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import java.util.Set;
@Entity
@Table(name = "bono_promocion")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BonoPromocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String tipo; //"Bienvenida, recarga, etc"

    @NotNull
    private Double valor; //monto o porcentaje

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaFin;

    @NotBlank
    private String condiciones;

    @ManyToOne(mappedBy = "bonos")
    private Set<Usuario> usuarios;
}
