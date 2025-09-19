package org.ide.qoribet.juegoazar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ide.qoribet.apuesta.entity.Apuesta;
import org.ide.qoribet.usuario.entity.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "juegos_azar")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class JuegoAzar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotBlank
    private String tipo;//"Ruleta, tragamonedas, etc"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date fechaCreacion = new Date();

    private String estado = "ACTIVO";

    @ManyToMany(mappedBy = "juegosJugados")
    private Set<Usuario> usuarios;

    @OneToMany(mappedBy = "juegoAzar", cascade = CascadeType.ALL)
    private List<Apuesta> apuestas;

}
