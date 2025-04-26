package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Teacher;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Teacher getById(UUID id) throws EntityNotFoundException {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher", id.toString()));
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(UUID id, Teacher teacher) throws EntityNotFoundException {
        Teacher existingTeacher = this.getById(id);

        existingTeacher.setName(teacher.getName());
        existingTeacher.setPhone(teacher.getPhone());
        existingTeacher.setAddress(teacher.getAddress());
        existingTeacher.setAge(teacher.getAge());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setLastname(teacher.getLastname());
        existingTeacher.setCourses(teacher.getCourses());

        return teacherRepository.save(existingTeacher);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        Teacher existingTeacher = this.getById(id);
        teacherRepository.delete(existingTeacher);
    }
}
