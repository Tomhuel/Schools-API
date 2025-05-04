package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.NotYearsOlderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotYearsOlderValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface NotYearsOlderThan {

    int years();

    String message() default "This date cannot be older than the years given";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
