package crosso.workshop.schools_api.validation;

import crosso.workshop.schools_api.annotation.NotYearsOlderThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class NotYearsOlderValidator implements ConstraintValidator<NotYearsOlderThan, Date> {

    private Date oldDate;

    @Override
    public void initialize(NotYearsOlderThan annotation) {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.YEAR, -annotation.years());
        this.oldDate = current.getTime();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return true;
        return date.after(this.oldDate);
    }
}
