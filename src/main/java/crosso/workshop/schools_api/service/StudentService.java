package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.exception.InvalidFieldException;
import crosso.workshop.schools_api.model.StudentDTO;
import crosso.workshop.schools_api.repository.StudentRepository;
import crosso.workshop.schools_api.utils.validation.Validator;
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

    public StudentDTO create(StudentDTO student) throws InvalidFieldException{
        this.validate(student);

        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);
        return mapper.map(studentEntity, StudentDTO.class);
    }

    public StudentDTO update(UUID id, StudentDTO student) throws InvalidFieldException {
        this.validate(student);

        student.setId(id);
        StudentEntity studentEntity = mapper.map(student, StudentEntity.class);
        studentEntity = studentRepository.save(studentEntity);
        return mapper.map(studentEntity, StudentDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        StudentDTO student = this.getById(id);
        studentRepository.delete(mapper.map(student, StudentEntity.class));
    }

    public void validate(StudentDTO student) throws InvalidFieldException {
        String classname = "Student";

        Validator.notNull(student.getName(), "Student's name cannot be null", classname, "name");
        Validator.notBlank(student.getName(), "Student's name cannot be blank", classname, "name");
        Validator.isStripped(student.getName(), "Student's name cannot contain any space character at the end and the start of the string", classname, "name");
        Validator.notContainNumbers(student.getName(), "Student's name cannot contain any number", classname, "name");
        Validator.notContainSpecialCharacters(student.getName(), "Student's name cannot contain any special character", classname, "name");

        Validator.notNull(student.getAge(), "Student's age cannot be null", classname, "age");
        Validator.greaterOrEqualThan(student.getAge(), 3, "Student's age must be greater or equal 3", classname, "age");
        Validator.lowerOrEqualThan(student.getAge(), 12, "Student's age must be lower or equal 12", classname, "age");

        Validator.notNull(student.getAddress(), "Student's address cannot be null", classname, "address");
        Validator.notBlank(student.getAddress(), "Student's address cannot be blank", classname, "address");
    }
}
