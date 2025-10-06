package Aerolinea.dto;

import Aerolinea.entity.Booking;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class BookingDTO {

    private Long id;
    private String flightNumber;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private Date bookingDate;
    private String status;

    // Flight details
    private String airlineName;
    private String origin;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
}
