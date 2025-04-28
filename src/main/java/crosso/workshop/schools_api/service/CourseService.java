package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.CourseEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.exception.InvalidFieldException;
import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.repository.CourseRepository;
import crosso.workshop.schools_api.utils.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public List<CourseDTO> getAll() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(c -> mapper.map(c, CourseDTO.class)).toList();
    }

    public CourseDTO getById(UUID id) throws EntityNotFoundException {
        CourseEntity course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course", id.toString()));
        return mapper.map(course, CourseDTO.class);
    }

    public CourseDTO create(CourseDTO course) throws InvalidFieldException {
        this.validate(course);

        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDTO.class);
    }

    public CourseDTO update(UUID id, CourseDTO course) throws InvalidFieldException {
        this.validate(course);
        course.setId(id);
        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        CourseDTO course = this.getById(id);
        courseRepository.delete(mapper.map(course, CourseEntity.class));
    }

    private void validate(CourseDTO course) throws InvalidFieldException {
        String classname = "Course";
        String courseCodeRegexp = "^[A-Z]{3}\\d$";

        Validator.notNull(course.getName(), "Course's name cannot be null", classname, "name");
        Validator.isStripped(course.getName(), "Course's name cannot have space characters at the start or the end of the string", classname, "name");
        Validator.notBlank(course.getName(), "Course's name cannot be blank", classname, "name");
        Validator.notContainNumbers(course.getName(), "Course's name cannot contain numbers", classname, "name");

        Validator.notNull(course.getDescription(), "Course's description cannot be null", classname, "description");

        Validator.notNull(course.getCode(), "Course's code cannot be null", classname, "code");
        Validator.customValidation(() -> course.getCode().matches(courseCodeRegexp), "Course code must have exactly 3 letters followed by 1 number.", classname, "code");
    }
}
