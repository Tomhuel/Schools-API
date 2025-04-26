package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Course;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
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

    public Course getById(UUID id) throws EntityNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course", id.toString()));
    }

    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public Course update(UUID id, Course course) throws EntityNotFoundException{
        Course existingCourse = this.getById(id);
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setSchool(course.getSchool());
        existingCourse.setStudents(course.getStudents());
        existingCourse.setTeacher(course.getTeacher());

        return courseRepository.save(existingCourse);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        Course existingCourse = this.getById(id);
        courseRepository.delete(existingCourse);
    }
}
