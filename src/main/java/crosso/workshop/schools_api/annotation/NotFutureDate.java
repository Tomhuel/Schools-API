package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.NotFutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotFutureDateValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface NotFutureDate {
    String message() default "This date cannot be from the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
