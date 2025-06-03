package crosso.workshop.schools_api.model;

import crosso.workshop.schools_api.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDetailDTO {
    private UUID id;

    @NotNull(message = "School's name cannot be null")
    @NotBlank(message = "School's name cannot be blank")
    @NotContainSpecialCharacters(message = "School's name cannot contain any special characters")
    @NotContainNumbers(message = "School's name cannot contain any number")
    @Trimmed(message = "School's name cannot contain any space character at the start or end of the string")
    private String name;

    @NotNull(message = "School's start date cannot be null")
    @NotYearsOlderThan(years = 200, message = "School's start date cannot be earlier from 200 years ago")
    @NotFutureDate(message = "School's start date cannot be from the future")
    private Date startDate;

    private Set<CourseReducedDTO> courses = new HashSet<>();
}
