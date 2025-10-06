package Aerolinea.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginDTO {

    @NotNull(message = "El email es obligatorio")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
