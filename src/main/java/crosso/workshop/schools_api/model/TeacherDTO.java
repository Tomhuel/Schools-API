package crosso.workshop.schools_api.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private UUID id;

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private String address;

    private Set<CourseDTO> courses;

    private Set<StudentDTO> students;
}
