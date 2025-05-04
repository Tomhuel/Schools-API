package crosso.workshop.schools_api.utils.response.field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorField {
    private String causedBy;
    private String description;
    private String rejectedValue;
}
