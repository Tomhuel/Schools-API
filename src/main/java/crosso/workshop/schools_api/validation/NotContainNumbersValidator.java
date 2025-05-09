package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotContainNumbers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotContainNumbersValidator implements ConstraintValidator<NotContainNumbers, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        return s.codePoints().noneMatch(Character::isDigit);
    }
}