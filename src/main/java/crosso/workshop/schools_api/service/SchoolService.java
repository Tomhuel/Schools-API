package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.SchoolEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.SchoolDetailDTO;
import crosso.workshop.schools_api.model.SchoolReducedDTO;
import crosso.workshop.schools_api.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper mapper;

    public List<SchoolReducedDTO> getAll() {
        List<SchoolEntity> schools = schoolRepository.findAll();
        return schools.stream().map(s -> mapper.map(s, SchoolReducedDTO.class)).toList();
    }

    public SchoolDetailDTO getById(UUID id) throws EntityNotFoundException {
        return mapper.map(getEntityById(id), SchoolDetailDTO.class);
    }

    public SchoolEntity getEntityById(UUID id) throws EntityNotFoundException {
        return schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School", id.toString()));
    }

    public SchoolDetailDTO create(SchoolDetailDTO school) {
        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);

        if (schoolEntity.getCourses() == null) {
            schoolEntity.setCourses(new HashSet<>());
        }

        return mapper.map(schoolEntity, SchoolDetailDTO.class);
    }

    public SchoolDetailDTO update(UUID id, SchoolDetailDTO school) throws EntityNotFoundException {
        if (!schoolRepository.existsById(id)) throw new EntityNotFoundException("School", id.toString());
        school.setId(id);
        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);
        return mapper.map(schoolEntity, SchoolDetailDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        SchoolDetailDTO school = this.getById(id);
        schoolRepository.delete(mapper.map(school, SchoolEntity.class));
    }
}
