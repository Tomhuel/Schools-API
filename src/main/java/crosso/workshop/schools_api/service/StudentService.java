package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Student;
import crosso.workshop.schools_api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(UUID id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public UUID update(UUID id, Student student) {
        Student existingStudent = this.getById(id);

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setCourses(student.getCourses());
        existingStudent.setTeachers(student.getTeachers());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setUuid(student.getUuid());

        return studentRepository.save(existingStudent).getUuid();
    }

    public void delete(UUID id) {
        Student existingStudent = this.getById(id);
        studentRepository.delete(existingStudent);
    }
}
