package Aerolinea.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "vuelos")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Vuelo {
    @Id
    @Column(unique = true, length = 6)
    @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "El numero de vuelo debe tener maximo 6 caracteres alfanumericos en mayusculas")
    @NotNull
    private String flightNumber;

    @NotNull
    private String airlineName;

    @NotNull
    private String origin;

    @NotNull
    private String destination;

    @NotNull
    private Date departureTime;

    @NotNull
    private Date arrivalTime;

    @NotNull
    @Min(value = 1, message = "Debe haber al menos 1 asiento disponible")
    private Integer availableSeats;

}
