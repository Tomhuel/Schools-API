package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneNumberValidatorTest {

    private final PhoneNumberValidator validator = new PhoneNumberValidator();

    @Test
    void validInput() {
        String input = "922459123";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        String input = "8888chorrimangera";
        String input2 = "+34 888888888";
        String input3 = "922 938 123";

        boolean result = validator.isValid(input, null);
        boolean result2 = validator.isValid(input2, null);
        boolean result3 = validator.isValid(input3, null);

        Assertions.assertFalse(result);
        Assertions.assertFalse(result2);
        Assertions.assertFalse(result3);
    }
}
