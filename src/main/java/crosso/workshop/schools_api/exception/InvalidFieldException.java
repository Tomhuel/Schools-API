package crosso.workshop.schools_api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidFieldException extends Exception {

    private final List<String> errors;
    private String description;

    public InvalidFieldException(String error, String entity, String property) {
        super(String.format("%s.%s", entity, property));
        this.errors = new ArrayList<>();
        this.errors.add(error);
        this.description = buildMessage(errors);
    }

    public InvalidFieldException(List<String> errors) {
        super("Invalid field validation");
        this.errors = errors;
        this.description = buildMessage(errors);
    }

    public String buildMessage(List<String> errors) {
        return "Validation failed with the following errors: " + String.join(";", errors);
    }
}
