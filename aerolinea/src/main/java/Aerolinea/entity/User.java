package Aerolinea.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = ".*[A-Z].*", message = "El nombre debe contener al menos una letra mayúscula")
    private String firstName;

    @NotNull
    @Pattern(regexp = ".*[A-Z].*", message = "El apellido debe contener al menos una letra mayúscula")
    private String lastName;

    @NotNull
    @Email(message = "Debe ser un email válido")
    @Column(unique = true)
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número")
    private String password;
}
