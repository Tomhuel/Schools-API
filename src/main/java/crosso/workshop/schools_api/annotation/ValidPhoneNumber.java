package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface ValidPhoneNumber {
    String message() default "Teacher's phone must have the correct format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
