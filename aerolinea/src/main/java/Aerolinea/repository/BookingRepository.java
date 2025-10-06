package Aerolinea.repository;

import Aerolinea.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByFlightNumber(String flightNumber);

    // Buscar reservas por customer ID y rango de fechas (para evitar conflictos de horario)
    @Query("SELECT b FROM Booking b JOIN Vuelo v ON b.flightNumber = v.flightNumber " +
           "WHERE b.customerId = :customerId AND b.status = 'CONFIRMED' " +
           "AND ((v.departureTime BETWEEN :startTime AND :endTime) " +
           "OR (v.arrivalTime BETWEEN :startTime AND :endTime) " +
           "OR (:startTime BETWEEN v.departureTime AND v.arrivalTime))")
    List<Booking> findConflictingBookings(@Param("customerId") Long customerId,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);

    // Contar reservas confirmadas para un vuelo
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.flightNumber = :flightNumber AND b.status = 'CONFIRMED'")
    Long countConfirmedBookingsByFlightNumber(@Param("flightNumber") String flightNumber);
}
