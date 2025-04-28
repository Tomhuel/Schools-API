package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.service.SchoolService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schools")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<SchoolDTO>>> getAllSchools() {
        List<SchoolDTO> schools = schoolService.getAll();
        APIResponse<List<SchoolDTO>> response = new APIResponse<>();
        response.setBody(schools);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDTO>> getByID(@PathVariable UUID id) {
        SchoolDTO school = schoolService.getById(id);
        APIResponse<SchoolDTO> response = new APIResponse<>();
        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    public ResponseEntity<APIResponse<SchoolDTO>> createSchool(@RequestBody SchoolDTO school) {
        school = schoolService.create(school);
        APIResponse<SchoolDTO> response = new APIResponse<>();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(school.getId())
                .toUri();

        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDTO>> updateSchool(@PathVariable UUID id, @RequestBody SchoolDTO school) {
        school = schoolService.update(id, school);
        APIResponse<SchoolDTO> response = new APIResponse<>();
        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(school);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteSchool(@PathVariable UUID id) {
        schoolService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        response.setBody("School deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
