package Aerolinea.service;

import Aerolinea.dto.VueloDTO;
import Aerolinea.entity.Vuelo;
import Aerolinea.repository.VueloRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class VueloService {
    private final VueloRepository vueloRepository;
    private static final Pattern FLIGHT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{1,6}$");

    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    public void validarVuelo(VueloDTO flightRequest) {
        // Validar campos requeridos
        if (flightRequest.getFlightNumber() == null || flightRequest.getFlightNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de vuelo es obligatorio");
        }
        if (flightRequest.getOrigin() == null || flightRequest.getOrigin().trim().isEmpty()) {
            throw new IllegalArgumentException("El origen es obligatorio");
        }
        if (flightRequest.getDestination() == null || flightRequest.getDestination().trim().isEmpty()) {
            throw new IllegalArgumentException("El destino es obligatorio");
        }
        if (flightRequest.getDepartureTime() == null) {
            throw new IllegalArgumentException("La hora de salida es obligatoria");
        }
        if (flightRequest.getArrivalTime() == null) {
            throw new IllegalArgumentException("La hora de llegada es obligatoria");
        }
        if (flightRequest.getAvailableSeats() == null || flightRequest.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("Los asientos disponibles deben ser mayor a 0");
        }
        if (flightRequest.getAirlineName() == null || flightRequest.getAirlineName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la aerolínea es obligatorio");
        }

        // Validar formato del número de vuelo
        if (!FLIGHT_NUMBER_PATTERN.matcher(flightRequest.getFlightNumber()).matches()) {
            throw new IllegalArgumentException("El número de vuelo debe tener máximo 6 caracteres alfanuméricos en mayúsculas");
        }

        // Validar que la hora de salida sea menor que la hora de llegada
        if (flightRequest.getDepartureTime().compareTo(flightRequest.getArrivalTime()) >= 0) {
            throw new IllegalArgumentException("La hora de salida debe ser anterior a la hora de llegada");
        }

        // Validar que el número de vuelo sea único
        if (vueloRepository.findByFlightNumber(flightRequest.getFlightNumber()).isPresent()) {
            throw new IllegalArgumentException("El número de vuelo ya existe");
        }
    }

    public String createVuelo(VueloDTO flightRequest) {
        validarVuelo(flightRequest);

        Vuelo vuelo = Vuelo.builder()
                .flightNumber(flightRequest.getFlightNumber())
                .airlineName(flightRequest.getAirlineName())
                .origin(flightRequest.getOrigin())
                .destination(flightRequest.getDestination())
                .departureTime(flightRequest.getDepartureTime())
                .arrivalTime(flightRequest.getArrivalTime())
                .availableSeats(flightRequest.getAvailableSeats())
                .build();

        Vuelo savedVuelo = vueloRepository.save(vuelo);
        return savedVuelo.getFlightNumber();
    }

    public List<VueloDTO> searchFlights(String flightNumber, String airlineName, Date departureFromDate, Date departureToDate) {
        List<Vuelo> vuelos = vueloRepository.searchFlights(flightNumber, airlineName, departureFromDate, departureToDate);

        return vuelos.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private VueloDTO convertToDTO(Vuelo vuelo) {
        VueloDTO dto = new VueloDTO();
        dto.setFlightNumber(vuelo.getFlightNumber());
        dto.setAirlineName(vuelo.getAirlineName());
        dto.setOrigin(vuelo.getOrigin());
        dto.setDestination(vuelo.getDestination());
        dto.setDepartureTime(vuelo.getDepartureTime());
        dto.setArrivalTime(vuelo.getArrivalTime());
        dto.setAvailableSeats(vuelo.getAvailableSeats());
        return dto;
    }
}
