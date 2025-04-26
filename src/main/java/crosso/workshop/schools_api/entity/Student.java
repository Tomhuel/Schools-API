package crosso.workshop.schools_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Integer age;
    private String address;

    @ManyToMany
    private Set<Course> courses;

    @ManyToMany
    private Set<Teacher> teachers;
}
