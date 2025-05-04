package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotContainSpecialCharacters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotContainSpecialCharactersValidator implements ConstraintValidator<NotContainSpecialCharacters, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.codePoints().noneMatch(c -> !Character.isLetter(c) && !Character.isSpaceChar(c));
    }
}
