package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Teacher;
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

    public Teacher getById(UUID id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public UUID update(UUID id, Teacher teacher) {
        Teacher existingTeacher = this.getById(id);

        existingTeacher.setName(teacher.getName());
        existingTeacher.setPhone(teacher.getPhone());
        existingTeacher.setAddress(teacher.getAddress());
        existingTeacher.setAge(teacher.getAge());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setLastname(teacher.getLastname());
        existingTeacher.setCourses(teacher.getCourses());
        existingTeacher.setUuid(teacher.getUuid());

        return teacherRepository.save(existingTeacher).getUuid();
    }

    public void delete(UUID id) {
        Teacher existingTeacher = this.getById(id);
        teacherRepository.delete(existingTeacher);
    }
}
