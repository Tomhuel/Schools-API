package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotContainSpaceCharacters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotContainSpaceCharactersValidator implements ConstraintValidator<NotContainSpaceCharacters, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.chars().noneMatch(Character::isSpaceChar);
    }
}
