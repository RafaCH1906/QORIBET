package Aerolinea.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BookFlightRequestDTO {

    @NotNull(message = "El número de vuelo es obligatorio")
    @NotBlank(message = "El número de vuelo no puede estar vacío")
    private String flightNumber;
}
