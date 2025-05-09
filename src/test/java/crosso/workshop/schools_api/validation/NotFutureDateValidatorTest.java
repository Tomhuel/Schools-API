package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class NotFutureDateValidatorTest {

    private final NotFutureDateValidator validator = new NotFutureDateValidator();

    @Test
    void validInput() {
        LocalDate localDate = LocalDate.of(2003, 12, 23);
        Date input = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        LocalDate localDate = LocalDate.of(4003, 12, 23);
        Date input = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean result = validator.isValid(input, null);
        Assertions.assertFalse(result);
    }
}
