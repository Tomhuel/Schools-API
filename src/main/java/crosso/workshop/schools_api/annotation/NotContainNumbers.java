package crosso.workshop.schools_api.annotation;

import crosso.workshop.schools_api.validation.NotContainNumbersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotContainNumbersValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotContainNumbers {
    String message() default "This field cannot contain any numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
