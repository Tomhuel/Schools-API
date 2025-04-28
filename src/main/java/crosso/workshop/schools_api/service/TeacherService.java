package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.TeacherEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.exception.InvalidFieldException;
import crosso.workshop.schools_api.model.TeacherDTO;
import crosso.workshop.schools_api.repository.TeacherRepository;
import crosso.workshop.schools_api.utils.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    public List<TeacherDTO> getAll() {
        List<TeacherEntity> teachers = teacherRepository.findAll();
        return teachers.stream().map(t -> mapper.map(t, TeacherDTO.class)).toList();
    }

    public TeacherDTO getById(UUID id) throws EntityNotFoundException {
        TeacherEntity teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher", id.toString()));
        return mapper.map(teacher, TeacherDTO.class);
    }

    public TeacherDTO create(TeacherDTO teacher) throws InvalidFieldException {
        this.validate(teacher);

        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);

        return mapper.map(teacherEntity, TeacherDTO.class);
    }

    public TeacherDTO update(UUID id, TeacherDTO teacher) throws InvalidFieldException {
        this.validate(teacher);

        teacher.setId(id);
        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);
        return mapper.map(teacherEntity, TeacherDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        TeacherDTO teacher = this.getById(id);
        teacherRepository.delete(mapper.map(teacher, TeacherEntity.class));
    }

    public void validate(TeacherDTO teacher) throws InvalidFieldException {
        String classname = "Teacher";
        String name = "name";
        String lastname = "lastname";
        String age = "age";
        String email = "email";
        String phone = "phone";
        String address = "address";

        Validator.notNull(teacher.getName(), "Teacher's name cannot be null", classname, name);
        Validator.notBlank(teacher.getName(), "Teacher's name cannot be blank", classname, name);
        Validator.notContainSpaceCharacters(teacher.getName(), "Teacher's name cannot contain any space character", classname, name);
        Validator.notContainNumbers(teacher.getName(), "Teacher's name cannot contain any number", classname, name);
        Validator.notContainSpecialCharacters(teacher.getName(), "Teacher's name cannot contain any special character", classname, name);
        Validator.isStripped(teacher.getName(), "Teacher's name cannot contain separation characters around the content", classname, name);

        Validator.notNull(teacher.getLastname(), "Teacher's lastname cannot be null", classname, lastname);
        Validator.notBlank(teacher.getLastname(), "Teacher's lastname cannot be blank", classname, lastname);
        Validator.notContainSpaceCharacters(teacher.getLastname(), "Teacher's lastname cannot contain any space character", classname, lastname);
        Validator.notContainNumbers(teacher.getLastname(), "Teacher's lastname cannot contain any number", classname, lastname);
        Validator.notContainSpecialCharacters(teacher.getLastname(), "Teacher's lastname cannot contain any special character", classname, lastname);
        Validator.isStripped(teacher.getLastname(), "Teacher's lastname cannot contain separation characters around the content", classname, lastname);

        Validator.notNull(teacher.getAge(), "Teacher's age cannot be null", classname, age);
        Validator.greaterOrEqualThan(teacher.getAge(), 23, "Teacher's must be greater or equal 23", classname, age);
        Validator.lowerOrEqualThan(teacher.getAge(), 73, "Teacher's must be lower or equal 73", classname, age);

        Validator.notNull(teacher.getEmail(), "Teacher's email cannot be null", classname, email);
        Validator.notBlank(teacher.getEmail(), "Teacher's email cannot be blank", classname, email);
        Validator.isEmail(teacher.getEmail(), "Teacher's email must have the correct format: {user}@{domain}.{tld}", classname, email);

        Validator.notNull(teacher.getPhone(), "Teacher's phone cannot be null", classname, phone);
        Validator.notBlank(teacher.getPhone(), "Teacher's phone cannot be blank", classname, phone);
        Validator.isPhoneNumber(teacher.getPhone(), "Teacher's phone must have the correct format", classname, phone);

        Validator.notNull(teacher.getAddress(), "Teacher's address cannot be null", classname, address);
        Validator.notBlank(teacher.getAddress(), "Teacher's address cannot be blank", classname, address);

    }
}
