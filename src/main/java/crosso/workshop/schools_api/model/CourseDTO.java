package crosso.workshop.schools_api.model;

import crosso.workshop.schools_api.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private UUID id;

    @NotNull(message = "Course's name cannot be null")
    @NotBlank(message = "Course's name cannot be blank")
    @NotContainSpecialCharacters(message = "Course's name cannot contain special characters")
    @NotContainNumbers(message = "Course's name cannot contain any numbers")
    @Trimmed(message = "Course's name cannot contain spaces at the start and the end of the string")
    private String name;

    @NotNull(message = "Course's description cannot be null")
    private String description;

    @NotNull(message = "Course's code cannot be null")
    @Pattern(regexp = "^[A-Z]{3}\\d$")
    private String code;

    private SchoolDTO school;

    private TeacherDTO teacher;

    private Set<StudentDTO> students;

}
