package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotFutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, Date> {
    private final Date current = new Date();

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return true;
        return date.before(current);
    }
}
