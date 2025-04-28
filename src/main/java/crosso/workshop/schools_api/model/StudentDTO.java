package crosso.workshop.schools_api.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private UUID id;

    private String name;
    private Integer age;
    private String address;

    private Set<CourseDTO> courses;

    private Set<TeacherDTO> teachers;
}
