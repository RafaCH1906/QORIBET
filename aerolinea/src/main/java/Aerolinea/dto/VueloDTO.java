package Aerolinea.dto;

import Aerolinea.validation.DepartureBeforeArrival;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Data
@DepartureBeforeArrival
public class VueloDTO {

    @NotNull(message = "El número de vuelo es obligatorio")
    @NotBlank(message = "El número de vuelo no puede estar vacío")
    @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "El número de vuelo debe tener máximo 6 caracteres alfanuméricos en mayúsculas")
    private String flightNumber;

    @NotNull(message = "El nombre de la aerolínea es obligatorio")
    @NotBlank(message = "El nombre de la aerolínea no puede estar vacío")
    private String airlineName;

    @NotNull(message = "El origen es obligatorio")
    @NotBlank(message = "El origen no puede estar vacío")
    private String origin;

    @NotNull(message = "El destino es obligatorio")
    @NotBlank(message = "El destino no puede estar vacío")
    private String destination;

    @NotNull(message = "La hora de salida es obligatoria")
    private Date departureTime;

    @NotNull(message = "La hora de llegada es obligatoria")
    private Date arrivalTime;

    @NotNull(message = "Los asientos disponibles son obligatorios")
    @Min(value = 1, message = "Debe haber al menos 1 asiento disponible")
    private Integer availableSeats;
}
