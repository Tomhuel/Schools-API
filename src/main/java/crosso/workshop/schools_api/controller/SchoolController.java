package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.service.SchoolService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schools")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @GetMapping()
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public ResponseEntity<APIResponse<List<SchoolDTO>>> getAllSchools() {
        List<SchoolDTO> schools = schoolService.getAll();
        APIResponse<List<SchoolDTO>> response = new APIResponse<>();
        response.setBody(schools);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public ResponseEntity<APIResponse<SchoolDTO>> getByID(@PathVariable UUID id) {
        SchoolDTO school = schoolService.getById(id);
        APIResponse<SchoolDTO> response = new APIResponse<>();
        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<SchoolDTO>> createSchool(@RequestBody @Valid SchoolDTO school) {
        school = schoolService.create(school);
        APIResponse<SchoolDTO> response = new APIResponse<>();

        URI location = uriFactory.buildFromCurrentRequestWithUuid(school.getId());

        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<SchoolDTO>> updateSchool(@PathVariable UUID id, @RequestBody @Valid SchoolDTO school) {
        school = schoolService.update(id, school);
        APIResponse<SchoolDTO> response = new APIResponse<>();
        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(school);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<String>> deleteSchool(@PathVariable UUID id) {
        schoolService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        response.setBody("School deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
