package crosso.workshop.schools_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private String address;

    @OneToMany(mappedBy = "teacher", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<CourseEntity> courses;
}
