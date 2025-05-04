package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.StrippedStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrippedStringValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface Stripped {
    String message() default "This field cannot contain any space characters at the start or the end of the file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
