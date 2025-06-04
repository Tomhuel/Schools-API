package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.entity.TeacherEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.student.StudentDetailDTO;
import crosso.workshop.schools_api.model.student.StudentReducedDTO;
import crosso.workshop.schools_api.model.teacher.TeacherDetailDTO;
import crosso.workshop.schools_api.model.teacher.TeacherReducedDTO;
import crosso.workshop.schools_api.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    public List<TeacherReducedDTO> getAll() {
        List<TeacherEntity> teachers = teacherRepository.findAll();
        return teachers.stream().map(t -> mapper.map(t, TeacherReducedDTO.class)).toList();
    }

    public Page<TeacherReducedDTO> getPaginated(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        Page<TeacherEntity> teachers = teacherRepository.findAll(pageable);
        return teachers.map(teacher -> mapper.map(teacher, TeacherReducedDTO.class));
    }

    public TeacherDetailDTO getById(UUID id) throws EntityNotFoundException {
        return mapper.map(getEntityById(id), TeacherDetailDTO.class);
    }

    public TeacherEntity getEntityById(UUID id) throws EntityNotFoundException {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher", id.toString()));
    }

    public Page<StudentReducedDTO> getStudentsPaginated(UUID teacherId, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> students = teacherRepository.getStudentsByTeacherId(teacherId, pageable);
        return students.map(student -> mapper.map(student, StudentReducedDTO.class));
    }

    public TeacherDetailDTO create(TeacherDetailDTO teacher) {
        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);

        if (teacherEntity.getCourses() == null) {
            teacherEntity.setCourses(new HashSet<>());
        }

        return mapper.map(teacherEntity, TeacherDetailDTO.class);
    }

    public TeacherDetailDTO update(UUID id, TeacherDetailDTO teacher) throws EntityNotFoundException {
        if (!teacherRepository.existsById(id)) throw new EntityNotFoundException("Course", id.toString());
        teacher.setId(id);
        TeacherEntity teacherEntity = mapper.map(teacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);
        return mapper.map(teacherEntity, TeacherDetailDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        TeacherDetailDTO teacher = this.getById(id);
        teacherRepository.delete(mapper.map(teacher, TeacherEntity.class));
    }
}
