package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.School;
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

    public School getById(UUID id) {
        return schoolRepository.findById(id).orElseThrow();
    }

    public UUID update(UUID id, School school) {
        School existingSchool = this.getById(id);
        existingSchool.setName(school.getName());
        existingSchool.setStartDate(school.getStartDate());

        return schoolRepository.save(existingSchool).getUuid();
    }

    public void delete(UUID id) {
        School existingSchool = this.getById(id);
        schoolRepository.delete(existingSchool);
    }
}
