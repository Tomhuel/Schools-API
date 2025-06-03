package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.student.StudentDetailDTO;
import crosso.workshop.schools_api.model.student.StudentReducedDTO;
import crosso.workshop.schools_api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public List<StudentReducedDTO> getAll() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream().map(s -> mapper.map(s, StudentReducedDTO.class)).toList();
    }

    public StudentDetailDTO getById(UUID id) throws EntityNotFoundException {
        return mapper.map(getEntityById(id), StudentDetailDTO.class);
    }

    public StudentEntity getEntityById(UUID id) throws EntityNotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student", id.toString()));
    }

    public StudentDetailDTO create(StudentDetailDTO student) {
        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);

        if (studentEntity.getCourses() == null) {
            studentEntity.setCourses(new HashSet<>());
        }

        return mapper.map(studentEntity, StudentDetailDTO.class);
    }

    public StudentDetailDTO update(UUID id, StudentDetailDTO student) throws EntityNotFoundException {
        if (!studentRepository.existsById(id)) throw new EntityNotFoundException("Course", id.toString());
        student.setId(id);
        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);
        return mapper.map(studentEntity, StudentDetailDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        StudentDetailDTO student = this.getById(id);
        studentRepository.delete(mapper.map(student, StudentEntity.class));
    }
}
