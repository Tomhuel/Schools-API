package crosso.workshop.schools_api.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReducedDTO {

    @NotNull(message = "Course's id cannot be null")
    private UUID id;

    private String name;
    private String description;
    private String code;
}
