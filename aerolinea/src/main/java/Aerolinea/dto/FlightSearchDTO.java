package Aerolinea.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class FlightSearchDTO {

    private String flightNumber;
    private String airlineName;
    private Date departureFromDate;
    private Date departureToDate;
}
