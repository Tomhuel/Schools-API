package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.Student;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
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

    public Student getById(UUID id) throws EntityNotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new crosso.workshop.schools_api.exception.EntityNotFoundException("Student", id.toString()));
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(UUID id, Student student) throws EntityNotFoundException {
        Student existingStudent = this.getById(id);

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setCourses(student.getCourses());
        existingStudent.setTeachers(student.getTeachers());
        existingStudent.setAddress(student.getAddress());

        return studentRepository.save(existingStudent);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        Student existingStudent = this.getById(id);
        studentRepository.delete(existingStudent);
    }
}
