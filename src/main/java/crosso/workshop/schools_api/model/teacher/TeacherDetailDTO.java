package crosso.workshop.schools_api.model.teacher;

import crosso.workshop.schools_api.annotation.*;
import crosso.workshop.schools_api.model.course.CourseReducedDTO;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetailDTO {

    private UUID id;

    @NotNull(message = "Teacher's name cannot be null")
    @NotBlank(message = "Teacher's name cannot be blank")
    @NotContainSpaceCharacters(message = "Teacher's name cannot contain any space character")
    @NotContainSpecialCharacters(message = "Teacher's name cannot contain any special character")
    @NotContainNumbers(message = "Teacher's name cannot contain any number")
    @Trimmed(message = "Teacher's name cannot contain separation characters around the content")
    private String name;

    @NotNull(message = "Teacher's lastname cannot be null")
    @NotBlank(message = "Teacher's lastname cannot be blank")
    @NotContainSpaceCharacters(message = "Teacher's lastname cannot contain any space character")
    @NotContainSpecialCharacters(message = "Teacher's lastname cannot contain any special character")
    @NotContainNumbers(message = "Teacher's lastname cannot contain any number")
    @Trimmed(message = "Teacher's lastname cannot contain separation characters around the content")
    private String lastname;

    @NotNull(message = "Teacher's email cannot be null")
    @NotBlank(message = "Teacher's email cannot be blank")
    @Email(message = "Teacher's email must have the correct format: {user}@{domain}.{tld}")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Teacher's email must have the correct format: {user}@{domain}.{tld}")
    private String email;

    @NotNull(message = "Teacher's phone number cannot be null")
    @NotBlank(message = "Teacher's phone number cannot be blank")
    @ValidPhoneNumber(message = "Teacher's phone must have the correct format")
    private String phone;

    @NotNull(message = "Teacher's age cannot be null")
    @Min(value = 23, message = "Teacher's age must be greater or equal 23")
    @Max(value = 73, message = "Teacher's age must be lower or equal 73")
    private Integer age;

    @NotNull(message = "Teacher's address cannot be null")
    @NotBlank(message = "Teacher's address cannot be blank")
    private String address;

    private Set<CourseReducedDTO> courses = new HashSet<>();
}
