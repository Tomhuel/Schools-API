package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.TeacherEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.TeacherDTO;
import crosso.workshop.schools_api.repository.TeacherRepository;
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

    public TeacherDTO create(TeacherDTO teacher) {
        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);

        return mapper.map(teacherEntity, TeacherDTO.class);
    }

    public TeacherDTO update(UUID id, TeacherDTO teacher) throws EntityNotFoundException {
        if (!teacherRepository.existsById(id)) throw new EntityNotFoundException("Course", id.toString());
        teacher.setId(id);
        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);
        return mapper.map(teacherEntity, TeacherDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        TeacherDTO teacher = this.getById(id);
        teacherRepository.delete(mapper.map(teacher, TeacherEntity.class));
    }
}
