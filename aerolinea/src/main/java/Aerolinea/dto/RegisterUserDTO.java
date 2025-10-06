package Aerolinea.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterUserDTO {

    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = ".*[A-Z].*", message = "El nombre debe contener al menos una letra mayúscula (A-Z)")
    private String firstName;

    @NotNull(message = "El apellido es obligatorio")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(regexp = ".*[A-Z].*", message = "El apellido debe contener al menos una letra mayúscula (A-Z)")
    private String lastName;

    @NotNull(message = "El email es obligatorio")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número")
    private String password;
}
