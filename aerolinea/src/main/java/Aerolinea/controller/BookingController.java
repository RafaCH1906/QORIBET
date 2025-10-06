package Aerolinea.controller;

import Aerolinea.dto.BookFlightRequestDTO;
import Aerolinea.dto.BookingDTO;
import Aerolinea.dto.NewIdDTO;
import Aerolinea.security.JwtTokenValidator;
import Aerolinea.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final BookingService bookingService;
    private final JwtTokenValidator jwtTokenValidator;

    public BookingController(BookingService bookingService, JwtTokenValidator jwtTokenValidator) {
        this.bookingService = bookingService;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    // PROTECTED - Endpoint para reservar vuelos
    @PostMapping("/flights/book")
    public ResponseEntity<NewIdDTO> bookFlight(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody BookFlightRequestDTO request) {

        try {
            // Validar token JWT
            if (!jwtTokenValidator.validateToken(authHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Extraer customer ID del token
            String userIdStr = jwtTokenValidator.extractUserIdFromToken(authHeader);
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Long customerId = Long.parseLong(userIdStr);

            // Realizar la reserva
            Long bookingId = bookingService.bookFlight(request, customerId);
            NewIdDTO response = new NewIdDTO(bookingId.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PROTECTED - Endpoint para ver detalles de una reserva
    @GetMapping("/flight/book/{id}")
    public ResponseEntity<BookingDTO> getBooking(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {

        try {
            // Validar token JWT
            if (!jwtTokenValidator.validateToken(authHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Extraer customer ID del token
            String userIdStr = jwtTokenValidator.extractUserIdFromToken(authHeader);
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Long customerId = Long.parseLong(userIdStr);

            // Obtener la reserva
            BookingDTO booking = bookingService.getBooking(id, customerId);
            return ResponseEntity.ok(booking);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PROTECTED - Endpoint adicional para ver todas las reservas del usuario
    @GetMapping("/flights/bookings")
    public ResponseEntity<java.util.List<BookingDTO>> getUserBookings(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        try {
            // Validar token JWT
            if (!jwtTokenValidator.validateToken(authHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Extraer customer ID del token
            String userIdStr = jwtTokenValidator.extractUserIdFromToken(authHeader);
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Long customerId = Long.parseLong(userIdStr);

            // Obtener todas las reservas del usuario
            java.util.List<BookingDTO> bookings = bookingService.getUserBookings(customerId);
            return ResponseEntity.ok(bookings);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PROTECTED - Endpoint para regenerar email de confirmación
    @PostMapping("/flight/book/{id}/resend-email")
    public ResponseEntity<String> resendBookingEmail(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long id) {

        try {
            // Validar token JWT
            if (!jwtTokenValidator.validateToken(authHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Extraer customer ID del token
            String userIdStr = jwtTokenValidator.extractUserIdFromToken(authHeader);
            if (userIdStr == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Long customerId = Long.parseLong(userIdStr);

            // Obtener la reserva y generar email
            BookingDTO booking = bookingService.getBooking(id, customerId);
            bookingService.resendConfirmationEmail(booking);

            return ResponseEntity.ok("Email de confirmación generado exitosamente");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
