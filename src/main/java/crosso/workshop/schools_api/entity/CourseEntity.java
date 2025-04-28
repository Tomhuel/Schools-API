package crosso.workshop.schools_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String code;

    @ManyToOne
    private SchoolEntity school;

    @ManyToOne
    private TeacherEntity teacher;

    @ManyToMany
    private Set<StudentEntity> students;

}
