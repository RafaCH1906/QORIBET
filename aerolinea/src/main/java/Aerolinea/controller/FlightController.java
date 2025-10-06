package Aerolinea.controller;

import Aerolinea.dto.VueloDTO;
import Aerolinea.security.JwtTokenValidator;
import Aerolinea.service.VueloService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final VueloService vueloService;
    private final JwtTokenValidator jwtTokenValidator;

    public FlightController(VueloService vueloService, JwtTokenValidator jwtTokenValidator) {
        this.vueloService = vueloService;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<VueloDTO> create(@Valid @RequestBody VueloDTO newFlight) {
        try {
            String flightId = vueloService.createVuelo(newFlight);
            // Devolver el mismo VueloDTO que se creó
            return ResponseEntity.status(HttpStatus.CREATED).body(newFlight);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PROTECTED - Requiere autenticación
    @GetMapping("/search")
    public ResponseEntity<List<VueloDTO>> search(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureFromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureToDate) {

        try {
            // Validar token JWT
            if (!jwtTokenValidator.validateToken(authHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Realizar búsqueda
            List<VueloDTO> flights = vueloService.searchFlights(flightNumber, airlineName, departureFromDate, departureToDate);
            return ResponseEntity.ok(flights);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
