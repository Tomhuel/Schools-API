package crosso.workshop.schools_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Integer age;
    private String address;

    @ManyToMany(mappedBy = "students")
    private Set<CourseEntity> courses;

    public Set<CourseEntity> addCourse(CourseEntity course) {
        this.courses.add(course);
        course.getStudents().add(this);
        return this.courses;
    }

    public Set<CourseEntity> removeCourse(CourseEntity course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
        return this.courses;
    }
}
