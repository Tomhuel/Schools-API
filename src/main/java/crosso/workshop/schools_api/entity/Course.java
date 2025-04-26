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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    @ManyToOne
    private School school;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private Set<Student> students;

}
