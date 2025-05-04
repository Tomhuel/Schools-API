package crosso.workshop.schools_api.utils.response;

import crosso.workshop.schools_api.utils.response.field.ErrorField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private List<ErrorField> errors = new ArrayList<>();
    private LocalDateTime timestamp = LocalDateTime.now();
    private String uri;
}
