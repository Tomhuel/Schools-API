package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.NotContainSpecialCharactersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotContainSpecialCharactersValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface NotContainSpecialCharacters {
    String message() default "This field cannot contain any space special characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
