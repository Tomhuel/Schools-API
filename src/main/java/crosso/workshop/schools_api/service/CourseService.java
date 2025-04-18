package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Course;
import crosso.workshop.schools_api.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(UUID id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public UUID update(UUID id, Course course) {
        Course existingCourse = this.getById(id);
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setSchool(course.getSchool());
        existingCourse.setStudents(course.getStudents());
        existingCourse.setTeacher(course.getTeacher());
        existingCourse.setUuid(course.getUuid());

        return courseRepository.save(existingCourse).getUuid();
    }

    public void delete(UUID id) {
        Course existingCourse = this.getById(id);
        courseRepository.delete(existingCourse);
    }
}
