package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.CourseEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.repository.CourseRepository;
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

    public CourseDTO create(CourseDTO course) {
        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDTO.class);
    }

    public CourseDTO update(UUID id, CourseDTO course) {
        course.setId(id);
        CourseEntity courseEntity = mapper.map(course, CourseEntity.class);
        courseEntity = courseRepository.save(courseEntity);
        return mapper.map(courseEntity, CourseDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        CourseDTO course = this.getById(id);
        courseRepository.delete(mapper.map(course, CourseEntity.class));
    }
}
