package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.Trimmed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimmedStringValidator implements ConstraintValidator<Trimmed, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        return s.equals(s.trim());
    }
}
