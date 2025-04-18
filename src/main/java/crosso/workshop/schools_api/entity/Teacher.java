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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Integer age;
    private String address;

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

    @ManyToMany
    private Set<Student> students;
}
