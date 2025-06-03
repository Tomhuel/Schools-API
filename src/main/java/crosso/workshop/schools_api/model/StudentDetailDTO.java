package crosso.workshop.schools_api.model;

import crosso.workshop.schools_api.annotation.*;
import crosso.workshop.schools_api.annotation.NotContainNumbers;
import crosso.workshop.schools_api.annotation.NotContainSpecialCharacters;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailDTO {

    private UUID id;

    @NotNull(message = "Student's name cannot be null")
    @NotBlank(message = "Student's name cannot be blank")
    @Trimmed(message = "Student's name cannot contain any space character at the end and the start of the string")
    @NotContainNumbers(message = "Student's name cannot contain any number")
    @NotContainSpecialCharacters(message = "Student's name cannot contain any special character")
    private String name;

    @NotNull(message = "Student's age cannot be null")
    @Min(value = 3, message = "Student's age must be greater or equal 3")
    @Max(value = 12, message = "Student's age must be lower or equal 12")
    private Integer age;

    @NotNull(message = "Student's address cannot be null")
    @NotBlank(message = "Student's address cannot be blank")
    private String address;

    private Set<CourseDetailDTO> courses = new HashSet<>();

    private Set<TeacherDetailDTO> teachers = new HashSet<>();
}
