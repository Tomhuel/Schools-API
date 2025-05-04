package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.ValidCourseCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCourseCodeValidator implements ConstraintValidator<ValidCourseCode, String> {

    private static final String REGEXP = "^[A-Z]{3}\\d$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(REGEXP);
    }
}