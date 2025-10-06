package Aerolinea.service;

import Aerolinea.dto.BookFlightRequestDTO;
import Aerolinea.dto.BookingDTO;
import Aerolinea.entity.Booking;
import Aerolinea.entity.User;
import Aerolinea.entity.Vuelo;
import Aerolinea.event.BookingConfirmedEvent;
import Aerolinea.repository.BookingRepository;
import Aerolinea.repository.UserRepository;
import Aerolinea.repository.VueloRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VueloRepository vueloRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(BookingRepository bookingRepository, VueloRepository vueloRepository,
                          UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.vueloRepository = vueloRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Long bookFlight(BookFlightRequestDTO request, Long customerId) {
        // Validar que el vuelo existe
        Optional<Vuelo> vueloOpt = vueloRepository.findByFlightNumber(request.getFlightNumber());
        if (vueloOpt.isEmpty()) {
            throw new IllegalArgumentException("Vuelo no encontrado");
        }

        Vuelo vuelo = vueloOpt.get();

        // Obtener información del cliente
        Optional<User> userOpt = userRepository.findById(customerId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOpt.get();

        // Validar que el vuelo no sea pasado o en tránsito
        Date now = new Date();
        if (vuelo.getDepartureTime().before(now)) {
            throw new IllegalArgumentException("No se puede reservar un vuelo que ya ha partido o está en tránsito");
        }

        // Verificar disponibilidad de asientos (no sobrevender)
        Long confirmedBookings = bookingRepository.countConfirmedBookingsByFlightNumber(request.getFlightNumber());
        if (confirmedBookings >= vuelo.getAvailableSeats()) {
            throw new IllegalArgumentException("No hay asientos disponibles para este vuelo");
        }

        // Verificar conflictos de horario para el cliente
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
            customerId, vuelo.getDepartureTime(), vuelo.getArrivalTime()
        );
        if (!conflictingBookings.isEmpty()) {
            throw new IllegalArgumentException("Tienes una reserva que conflicta con el horario de este vuelo");
        }

        // Crear la reserva
        Booking booking = Booking.builder()
                .flightNumber(request.getFlightNumber())
                .customerId(customerId)
                .customerFirstName(user.getFirstName())
                .customerLastName(user.getLastName())
                .customerEmail(user.getEmail())
                .bookingDate(new Date())
                .status(Booking.BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        // Publicar evento para generar email de confirmación
        try {
            BookingDTO bookingWithDetails = getBooking(savedBooking.getId(), customerId);
            eventPublisher.publishEvent(new BookingConfirmedEvent(this, bookingWithDetails));
        } catch (Exception e) {
            System.err.println("Error al publicar evento de confirmación: " + e.getMessage());
        }

        return savedBooking.getId();
    }

    public BookingDTO getBooking(Long bookingId, Long customerId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Reserva no encontrada");
        }

        Booking booking = bookingOpt.get();

        // Verificar que la reserva pertenece al cliente
        if (!booking.getCustomerId().equals(customerId)) {
            throw new IllegalArgumentException("No tienes permiso para ver esta reserva");
        }

        // Obtener detalles del vuelo
        Optional<Vuelo> vueloOpt = vueloRepository.findByFlightNumber(booking.getFlightNumber());
        if (vueloOpt.isEmpty()) {
            throw new IllegalArgumentException("Detalles del vuelo no encontrados");
        }

        Vuelo vuelo = vueloOpt.get();

        // Crear DTO con toda la información
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setFlightNumber(booking.getFlightNumber());
        dto.setCustomerId(booking.getCustomerId());
        dto.setCustomerFirstName(booking.getCustomerFirstName());
        dto.setCustomerLastName(booking.getCustomerLastName());
        dto.setCustomerEmail(booking.getCustomerEmail());
        dto.setBookingDate(booking.getBookingDate());
        dto.setStatus(booking.getStatus().toString());

        // Agregar detalles del vuelo
        dto.setAirlineName(vuelo.getAirlineName());
        dto.setOrigin(vuelo.getOrigin());
        dto.setDestination(vuelo.getDestination());
        dto.setDepartureTime(vuelo.getDepartureTime());
        dto.setArrivalTime(vuelo.getArrivalTime());

        return dto;
    }

    public List<BookingDTO> getUserBookings(Long customerId) {
        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);

        return bookings.stream()
                .map(booking -> {
                    try {
                        return getBooking(booking.getId(), customerId);
                    } catch (Exception e) {
                        // En caso de error, crear DTO básico
                        BookingDTO dto = new BookingDTO();
                        dto.setId(booking.getId());
                        dto.setFlightNumber(booking.getFlightNumber());
                        dto.setCustomerId(booking.getCustomerId());
                        dto.setCustomerFirstName(booking.getCustomerFirstName());
                        dto.setCustomerLastName(booking.getCustomerLastName());
                        dto.setCustomerEmail(booking.getCustomerEmail());
                        dto.setBookingDate(booking.getBookingDate());
                        dto.setStatus(booking.getStatus().toString());
                        return dto;
                    }
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public void resendConfirmationEmail(BookingDTO booking) {
        // Publicar evento para regenerar email
        eventPublisher.publishEvent(new BookingConfirmedEvent(this, booking));
    }
}
