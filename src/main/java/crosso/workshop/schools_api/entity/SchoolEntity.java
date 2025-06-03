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
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Date startDate;

    @OneToMany(mappedBy = "school", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<CourseEntity> courses;

    public Set<CourseEntity> addCourse(CourseEntity course) {
        this.courses.add(course);
        course.setSchool(this);
        return this.courses;
    }

    public Set<CourseEntity> removeCourse(CourseEntity course) {
        this.courses.remove(course);
        course.setSchool(null);
        return this.courses;
    }
}
