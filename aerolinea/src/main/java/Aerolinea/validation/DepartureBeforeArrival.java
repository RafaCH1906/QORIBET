package Aerolinea.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepartureBeforeArrivalValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DepartureBeforeArrival {
    String message() default "La hora de salida debe ser anterior a la hora de llegada";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
