package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotYearsOlderThan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class NotYearsOlderValidatorTest {

    private final NotYearsOlderValidator validator = new NotYearsOlderValidator();

    @Test
    void validInput() {
        NotYearsOlderThan annotation = Mockito.mock(NotYearsOlderThan.class);
        Mockito.when(annotation.years()).thenReturn(200);

        validator.initialize(annotation);

        LocalDate localDate = LocalDate.of(2003, 12, 23);
        Date input = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result);
    }

    @Test
    void invalidInput() {
        NotYearsOlderThan annotation = Mockito.mock(NotYearsOlderThan.class);
        Mockito.when(annotation.years()).thenReturn(200);

        validator.initialize(annotation);

        LocalDate localDate = LocalDate.of(1203, 12, 23);
        Date input = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean result = validator.isValid(input, null);
        Assertions.assertFalse(result);
    }
}
