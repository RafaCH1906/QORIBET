package Aerolinea.validation;

import Aerolinea.dto.VueloDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartureBeforeArrivalValidator implements ConstraintValidator<DepartureBeforeArrival, VueloDTO> {

    @Override
    public void initialize(DepartureBeforeArrival constraintAnnotation) {
    }

    @Override
    public boolean isValid(VueloDTO flight, ConstraintValidatorContext context) {
        if (flight == null || flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
            return true;
        }

        return flight.getDepartureTime().before(flight.getArrivalTime());
    }
}
