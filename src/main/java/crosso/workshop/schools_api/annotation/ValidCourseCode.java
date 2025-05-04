package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.ValidCourseCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

/*
* There're 2 ways to validate:
* 1. Validate a field
* 2. Validate the whole object
*/


@Documented
@Constraint(validatedBy = ValidCourseCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@ReportAsSingleViolation
public @interface ValidCourseCode {
    String message() default "Course code must have exactly 3 letters followed by 1 number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
