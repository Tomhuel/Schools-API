package crosso.workshop.schools_api.model.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherReducedDTO {

    @NotNull(message = "Teacher's id cannot be null")
    private UUID id;

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private String address;
}
