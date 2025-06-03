package crosso.workshop.schools_api.model.course;

import crosso.workshop.schools_api.annotation.NotContainNumbers;
import crosso.workshop.schools_api.annotation.NotContainSpecialCharacters;
import crosso.workshop.schools_api.annotation.Trimmed;
import crosso.workshop.schools_api.model.school.SchoolReducedDTO;
import crosso.workshop.schools_api.model.student.StudentReducedDTO;
import crosso.workshop.schools_api.model.teacher.TeacherReducedDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailDTO {

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

    @NotNull(message = "Course's school cannot be null")
    private SchoolReducedDTO school;

    @NotNull(message = "Course's teacher cannot be null")
    private TeacherReducedDTO teacher;

    private Set<StudentReducedDTO> students = new HashSet<>();

}
