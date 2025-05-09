package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidCourseCodeValidatorTest {

    private final ValidCourseCodeValidator validator = new ValidCourseCodeValidator();

    @Test
    void validInput() {
        String input = "LEN2";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        String input = "ABC 3";
        String input2 = "3ABC";
        String input3 = "1234";
        String input4 = "LONG";

        boolean result = validator.isValid(input, null);
        boolean result2 = validator.isValid(input2, null);
        boolean result3 = validator.isValid(input3, null);
        boolean result4 = validator.isValid(input4, null);

        Assertions.assertFalse(result);
        Assertions.assertFalse(result2);
        Assertions.assertFalse(result3);
        Assertions.assertFalse(result4);
    }
}
