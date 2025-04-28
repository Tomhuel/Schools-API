package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.SchoolEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.exception.InvalidFieldException;
import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.repository.SchoolRepository;
import crosso.workshop.schools_api.utils.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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

    public SchoolDTO create(SchoolDTO school) throws InvalidFieldException {
        this.validate(school);

        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);
        return mapper.map(schoolEntity, SchoolDTO.class);
    }

    public SchoolDTO update(UUID id, SchoolDTO school) throws InvalidFieldException {
        this.validate(school);

        school.setId(id);
        SchoolEntity schoolEntity = mapper.map(school, SchoolEntity.class);
        schoolEntity = schoolRepository.save(schoolEntity);
        return mapper.map(schoolEntity, SchoolDTO.class);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        SchoolDTO school = this.getById(id);
        schoolRepository.delete(mapper.map(school, SchoolEntity.class));
    }

    private void validate(SchoolDTO school) throws InvalidFieldException {
        String classname = "School";

        Calendar current = Calendar.getInstance();
        current.add(Calendar.YEAR, -200);
        Date currentDate = current.getTime();

        Validator.notNull(school.getName(), "School's name cannot be null", classname, "name");
        Validator.notBlank(school.getName(), "School's name cannot be blank", classname, "name");
        Validator.notContainSpecialCharacters(school.getName(), "School's name cannot contain special characters", classname, "name");
        Validator.notContainNumbers(school.getName(), "School's name cannot contain numbers", classname, "name");
        Validator.isStripped(school.getName(), "School's name cannot contain separation characters around the content", classname, "name");

        Validator.notNull(school.getStartDate(), "School's start date cannot be null", classname, "startDate");
        Validator.lowerThan(school.getStartDate(), new Date(), "School's start date cannot be from the future", classname, "startDate");
        Validator.greaterThan(school.getStartDate(), currentDate, "School's start date cannot be earlier from 200 years ago", classname, "startDate");
    }
}
