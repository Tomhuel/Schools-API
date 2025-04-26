package crosso.workshop.schools_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity(name = "schools")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Date startDate;

    @OneToMany(mappedBy = "school", cascade = CascadeType.REMOVE)
    private Set<Course> courses;
}
