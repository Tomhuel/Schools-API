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
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    @ManyToMany
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentEntity> students;

    public Set<StudentEntity> addStudent(StudentEntity student) {
        this.students.add(student);
        student.getCourses().add(this);
        return this.students;
    }

    public Set<StudentEntity> removeStudent(StudentEntity student) {
        this.students.remove(student);
        student.getCourses().remove(this);
        return this.students;
    }
}
