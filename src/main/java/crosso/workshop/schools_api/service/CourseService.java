package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.CourseEntity;
import crosso.workshop.schools_api.entity.SchoolEntity;
import crosso.workshop.schools_api.entity.TeacherEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.course.CourseDetailDTO;
import crosso.workshop.schools_api.model.course.CourseReducedDTO;
import crosso.workshop.schools_api.model.course.CourseSearchDTO;
import crosso.workshop.schools_api.model.school.SchoolReducedDTO;
import crosso.workshop.schools_api.model.teacher.TeacherReducedDTO;
import crosso.workshop.schools_api.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private final SchoolService schoolService;
    private final TeacherService teacherService;

    public List<CourseReducedDTO> getAll() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(c -> mapper.map(c, CourseReducedDTO.class)).toList();
    }

    public Page<CourseReducedDTO> getPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEntity> courses = courseRepository.findAll(pageable);
        return courses.map(course -> mapper.map(course, CourseReducedDTO.class));
    }

    public CourseDetailDTO getById(UUID id) throws EntityNotFoundException {
        return mapper.map(this.getEntityById(id), CourseDetailDTO.class);
    }

    public CourseEntity getEntityById(UUID id) throws EntityNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course", id.toString()));
    }

    public Page<CourseReducedDTO> searchCourses(
            CourseSearchDTO courseSearch,
            String sortBy,
            ExampleMatcher.StringMatcher match,
            Sort.Direction direction,
            int page,
            int size
    ) {

        CourseEntity courseEntity = mapper.map(courseSearch, CourseEntity.class);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(match)
                .withIgnoreCase();

        Example<CourseEntity> example = Example.of(courseEntity, matcher);

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CourseEntity> courses = courseRepository.findAll(example, pageable);

        return courses.map(c -> mapper.map(c, CourseReducedDTO.class));
    }

    public CourseDetailDTO create(CourseDetailDTO course) throws EntityNotFoundException {
        // Extraer los DTOs reducidos
        SchoolReducedDTO schoolDTO = course.getSchool();
        TeacherReducedDTO teacherDTO = course.getTeacher();

        // Comprobar que las entidades existen (lanzar excepción si no)
        SchoolEntity school = schoolService.getEntityById(schoolDTO.getId());
        TeacherEntity teacher = teacherService.getEntityById(teacherDTO.getId());

        // Mapear el curso
        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);

        // Settear las entidades completas para devolver su información correspondiente
        courseEntity.setTeacher(teacher);
        courseEntity.setSchool(school);

        if (courseEntity.getStudents() == null) {
            courseEntity.setStudents(new HashSet<>());
        }

        // Guardar y retornar
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDetailDTO.class);
    }


    public CourseDetailDTO update(UUID id, CourseDetailDTO course) throws EntityNotFoundException {
        if (!courseRepository.existsById(id)) throw new EntityNotFoundException("Course", id.toString());
        course.setId(id);
        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDetailDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        CourseDetailDTO course = this.getById(id);
        courseRepository.delete(mapper.map(course, CourseEntity.class));
    }
}
