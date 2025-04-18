package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.entity.School;
import crosso.workshop.schools_api.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    public ResponseEntity<List<School>>
}
