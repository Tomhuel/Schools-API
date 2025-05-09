package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotContainSpecialCharactersValidatorTest {

    private final NotContainSpecialCharactersValidator validator = new NotContainSpecialCharactersValidator();

    @Test
    void validInput() {
        String input = "Hello Im the 1st one here";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        String input = "I want get a lot of $$";
        boolean result = validator.isValid(input, null);
        Assertions.assertFalse(result);
    }
}
