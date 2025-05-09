package crosso.workshop.schools_api.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrimmedStringValidatorTest {

    private final TrimmedStringValidator validator = new TrimmedStringValidator();

    @Test
    void validInput() {
        String input = "This is a completely normal string";
        boolean result = validator.isValid(input, null);
        Assertions.assertTrue(result); // No hay espacios extra, por lo que es válido
    }

    @Test
    void invalidInput() {
        String input1 = "  This have some spaces around   ";
        String input2 = "\naND THIS one Have this -> \n";
        String input3 = "Do u like more ' ' or '\t";

        boolean result1 = validator.isValid(input1, null);
        boolean result2 = validator.isValid(input2, null);
        boolean result3 = validator.isValid(input3, null);

        Assertions.assertFalse(result1); // Tiene espacios extra
        Assertions.assertFalse(result2); // Tiene saltos de línea, no está 'stripped'
        Assertions.assertFalse(result3); // Tiene espacios y tabuladores
    }
}
