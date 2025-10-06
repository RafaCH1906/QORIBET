package Aerolinea.repository;

import Aerolinea.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VueloRepository extends JpaRepository<Vuelo, String> {
    Optional<Vuelo> findByFlightNumber(String flightNumber);

    List<Vuelo> findByFlightNumberContainingIgnoreCase(String flightNumber);

    List<Vuelo> findByAirlineNameContainingIgnoreCase(String airlineName);

    List<Vuelo> findByDepartureTimeBetween(Date startDate, Date endDate);

    @Query("SELECT v FROM Vuelo v WHERE " +
           "(:flightNumber IS NULL OR LOWER(v.flightNumber) LIKE LOWER(CONCAT('%', :flightNumber, '%'))) AND " +
           "(:airlineName IS NULL OR LOWER(v.airlineName) LIKE LOWER(CONCAT('%', :airlineName, '%'))) AND " +
           "(:startDate IS NULL OR v.departureTime >= :startDate) AND " +
           "(:endDate IS NULL OR v.departureTime <= :endDate)")
    List<Vuelo> searchFlights(@Param("flightNumber") String flightNumber,
                             @Param("airlineName") String airlineName,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate);
}
