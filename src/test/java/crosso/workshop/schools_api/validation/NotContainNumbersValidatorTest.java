package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NotContainNumbersValidatorTest {

    private final NotContainNumbersValidator validator = new NotContainNumbersValidator();

    @Test
    void validInput() {
        String input = "$$Abra Cadabra$$";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        String input = "John Doe 2nd";
        boolean result = validator.isValid(input, null);
        Assertions.assertFalse(result);
    }
}
