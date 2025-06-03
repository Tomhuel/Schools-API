package crosso.workshop.schools_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentReducedDTO {

    @NotNull(message = "Student's id cannot be null")
    private UUID id;

    private String name;
    private Integer age;
    private String address;
}
