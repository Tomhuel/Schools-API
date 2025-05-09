package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.SchoolEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper mapper;

    public List<SchoolDTO> getAll() {
        List<SchoolEntity> schools = schoolRepository.findAll();
        return schools.stream().map(s -> mapper.map(s, SchoolDTO.class)).toList();
    }

    public SchoolDTO getById(UUID id) throws EntityNotFoundException {
        SchoolEntity school = schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School", id.toString()));
        return mapper.map(school, SchoolDTO.class);
    }

    public SchoolDTO create(SchoolDTO school) {
        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);
        return mapper.map(schoolEntity, SchoolDTO.class);
    }

    public SchoolDTO update(UUID id, SchoolDTO school) throws EntityNotFoundException {
        if (!schoolRepository.existsById(id)) throw new EntityNotFoundException("School", id.toString());
        school.setId(id);
        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);
        return mapper.map(schoolEntity, SchoolDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        SchoolDTO school = this.getById(id);
        schoolRepository.delete(mapper.map(school, SchoolEntity.class));
    }
}
