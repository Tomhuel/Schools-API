package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.School;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public List<School> getAll() {
        return schoolRepository.findAll();
    }

    public School getById(UUID id) throws EntityNotFoundException {
        return schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School", id.toString()));
    }

    public School create(School school) {
        return schoolRepository.save(school);
    }

    public School update(UUID id, School school) throws EntityNotFoundException {
        School existingSchool = this.getById(id);
        existingSchool.setName(school.getName());
        existingSchool.setStartDate(school.getStartDate());

        return schoolRepository.save(existingSchool);
    }

    public void delete(UUID id) throws EntityNotFoundException {
        School existingSchool = this.getById(id);
        schoolRepository.delete(existingSchool);
    }
}
