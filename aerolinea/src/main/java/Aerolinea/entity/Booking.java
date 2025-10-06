package Aerolinea.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "flight_number")
    private String flightNumber;

    @NotNull
    @Column(name = "customer_id")
    private Long customerId;

    @NotNull
    @Column(name = "customer_first_name")
    private String customerFirstName;

    @NotNull
    @Column(name = "customer_last_name")
    private String customerLastName;

    @NotNull
    @Column(name = "customer_email")
    private String customerEmail;

    @NotNull
    @Column(name = "booking_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    public enum BookingStatus {
        CONFIRMED,
        CANCELLED,
        PENDING
    }
}
