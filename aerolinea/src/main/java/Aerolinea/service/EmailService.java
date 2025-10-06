package Aerolinea.service;

import Aerolinea.dto.BookingDTO;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Date;

@Service
public class EmailService {

    private static final String EMAIL_DIRECTORY = "booking_emails";
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public void sendBookingConfirmationEmail(BookingDTO booking) {
        try {
            // Crear directorio si no existe
            createEmailDirectory();

            // Generar contenido del email
            String emailContent = generateBookingEmailContent(booking);

            // Guardar archivo
            String fileName = String.format("flight_booking_email_%d.txt", booking.getId());
            saveEmailToFile(fileName, emailContent);

            System.out.println("Email de confirmación guardado: " + fileName);

        } catch (Exception e) {
            System.err.println("Error al generar email de confirmación: " + e.getMessage());
            // No lanzar excepción para no interrumpir el proceso de reserva
        }
    }

    private void createEmailDirectory() throws IOException {
        Path emailDir = Paths.get(EMAIL_DIRECTORY);
        if (!Files.exists(emailDir)) {
            Files.createDirectories(emailDir);
        }
    }

    private String generateBookingEmailContent(BookingDTO booking) {
        StringBuilder content = new StringBuilder();

        content.append("=== CONFIRMACIÓN DE RESERVA DE VUELO ===\n\n");

        // Información del cliente
        content.append("DATOS DEL PASAJERO:\n");
        content.append("Nombre: ").append(booking.getCustomerFirstName())
               .append(" ").append(booking.getCustomerLastName()).append("\n");
        content.append("Email: ").append(booking.getCustomerEmail()).append("\n\n");

        // Información de la reserva
        content.append("DETALLES DE LA RESERVA:\n");
        content.append("ID de Reserva: ").append(booking.getId()).append("\n");
        content.append("Número de Vuelo: ").append(booking.getFlightNumber()).append("\n");
        content.append("Estado: ").append(booking.getStatus()).append("\n");
        content.append("Fecha de Reserva: ").append(formatDateToISO8601(booking.getBookingDate())).append("\n\n");

        // Información del vuelo
        content.append("DETALLES DEL VUELO:\n");
        if (booking.getAirlineName() != null) {
            content.append("Aerolínea: ").append(booking.getAirlineName()).append("\n");
        }
        if (booking.getOrigin() != null && booking.getDestination() != null) {
            content.append("Ruta: ").append(booking.getOrigin())
                   .append(" → ").append(booking.getDestination()).append("\n");
        }
        if (booking.getDepartureTime() != null) {
            content.append("Fecha y Hora de Salida: ").append(formatDateToISO8601(booking.getDepartureTime())).append("\n");
        }
        if (booking.getArrivalTime() != null) {
            content.append("Fecha y Hora de Llegada: ").append(formatDateToISO8601(booking.getArrivalTime())).append("\n");
        }

        content.append("\n=== ¡Buen viaje! ===\n");
        content.append("Gracias por elegir nuestros servicios.\n");
        content.append("Presente este comprobante en el aeropuerto.\n");

        return content.toString();
    }

    private String formatDateToISO8601(Date date) {
        if (date == null) {
            return "N/A";
        }
        return date.toInstant()
                   .atZone(ZoneId.systemDefault())
                   .format(ISO_FORMATTER);
    }

    private void saveEmailToFile(String fileName, String content) throws IOException {
        Path filePath = Paths.get(EMAIL_DIRECTORY, fileName);

        try (FileWriter writer = new FileWriter(filePath.toFile(), false)) {
            writer.write(content);
        }
    }
}
