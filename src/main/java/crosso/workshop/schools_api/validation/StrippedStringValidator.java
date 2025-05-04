package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.Stripped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrippedStringValidator implements ConstraintValidator<Stripped, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.equals(s.strip());
    }
}
