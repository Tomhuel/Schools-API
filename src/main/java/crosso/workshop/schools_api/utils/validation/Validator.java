package crosso.workshop.schools_api.utils.validation;

import crosso.workshop.schools_api.exception.InvalidFieldException;

import java.util.Date;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class Validator {

    private Validator() {}

    public static void customValidation(BooleanSupplier booleanSupplier, String message, String entity, String property) throws InvalidFieldException {
        if (!booleanSupplier.getAsBoolean()) {
            throw new InvalidFieldException(message, entity, property);
        }
    }

    public static void isStripped(String string, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> string.equals(string.strip());
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void isTrimmed(String string, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> string.equals(string.trim());
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void notNull(Object object, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> Objects.nonNull(object);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void notBlank(String string, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> !string.isBlank();
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void greaterThan(Integer n, Integer m, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> n > m;
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void lowerThan(Integer n, Integer m, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> n < m;
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void greaterOrEqualThan(Integer n, Integer m, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> n >= m;
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void lowerOrEqualThan(Integer n, Integer m, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> n <= m;
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void isEmail(String email, String message, String entity, String property) throws InvalidFieldException {
        String emailRegexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        BooleanSupplier booleanSupplier = () -> email.matches(emailRegexp);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void isPhoneNumber(String phoneNumber, String message, String entity, String property) throws InvalidFieldException {
        String phoneNumberRegexp = "^\\d{9}$";
        BooleanSupplier booleanSupplier = () -> phoneNumber.matches(phoneNumberRegexp);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void greaterThan(Date date1, Date date2, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> date1.after(date2);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void lowerThan(Date date1, Date date2, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> date1.before(date2);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void notContainNumbers(String string, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> string.codePoints().noneMatch(Character::isDigit);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void notContainSpecialCharacters(String string, String message, String entity, String property) throws InvalidFieldException {
        // Validate if any character is not a letter or a space
        BooleanSupplier booleanSupplier = () -> string.codePoints().noneMatch(c -> !Character.isLetter(c) && !Character.isSpaceChar(c));
        Validator.customValidation(booleanSupplier, message, entity, property);
    }

    public static void notContainSpaceCharacters(String string, String message, String entity, String property) throws InvalidFieldException {
        BooleanSupplier booleanSupplier = () -> string.chars().noneMatch(Character::isSpaceChar);
        Validator.customValidation(booleanSupplier, message, entity, property);
    }
}