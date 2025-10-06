package Aerolinea.event;

import Aerolinea.dto.BookingDTO;
import org.springframework.context.ApplicationEvent;

public class BookingConfirmedEvent extends ApplicationEvent {

    private final BookingDTO booking;

    public BookingConfirmedEvent(Object source, BookingDTO booking) {
        super(source);
        this.booking = booking;
    }

    public BookingDTO getBooking() {
        return booking;
    }
}
