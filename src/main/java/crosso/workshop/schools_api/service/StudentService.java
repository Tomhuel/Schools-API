package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.StudentDTO;
import crosso.workshop.schools_api.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public List<StudentDTO> getAll() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream().map(s -> mapper.map(s, StudentDTO.class)).toList();
    }

    public StudentDTO getById(UUID id) throws EntityNotFoundException {
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student", id.toString()));
        return mapper.map(student, StudentDTO.class);
    }

    public StudentDTO create(StudentDTO student) {
        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);
        return mapper.map(studentEntity, StudentDTO.class);
    }

    public StudentDTO update(UUID id, StudentDTO student) {
        student.setId(id);
        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);
        return mapper.map(studentEntity, StudentDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        StudentDTO student = this.getById(id);
        studentRepository.delete(mapper.map(student, StudentEntity.class));
    }
}
