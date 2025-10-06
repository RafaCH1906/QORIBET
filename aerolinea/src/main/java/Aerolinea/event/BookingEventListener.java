package Aerolinea.event;

import Aerolinea.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BookingEventListener {

    private final EmailService emailService;

    public BookingEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleBookingConfirmed(BookingConfirmedEvent event) {
        try {
            // Generar email de confirmación de forma asíncrona
            emailService.sendBookingConfirmationEmail(event.getBooking());
            System.out.println("Email de confirmación enviado para reserva: " + event.getBooking().getId());
        } catch (Exception e) {
            System.err.println("Error al enviar email de confirmación: " + e.getMessage());
            // Log del error pero no interrumpir el flujo principal
        }
    }
}
