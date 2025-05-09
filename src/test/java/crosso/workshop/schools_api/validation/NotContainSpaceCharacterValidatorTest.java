package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotContainSpaceCharacterValidatorTest {

    private final NotContainSpaceCharactersValidator validator = new NotContainSpaceCharactersValidator();

    @Test
    void validInput() {
        String input = "$p41n";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        String input = "New Zealand";
        boolean result = validator.isValid(input, null);
        Assertions.assertFalse(result);
    }
}
