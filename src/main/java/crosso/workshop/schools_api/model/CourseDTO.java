package crosso.workshop.schools_api.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private UUID id;
    private String name;
    private String description;
    private String code;

    private SchoolDTO school;

    private TeacherDTO teacher;

    private Set<StudentDTO> students;

}
