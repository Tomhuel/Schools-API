package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.SchoolDetailDTO;
import crosso.workshop.schools_api.model.SchoolReducedDTO;
import crosso.workshop.schools_api.service.SchoolService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<APIResponse<List<SchoolReducedDTO>>> getAllSchools() {
        List<SchoolReducedDTO> schools = schoolService.getAll();
        APIResponse<List<SchoolReducedDTO>> response = new APIResponse<>();
        response.setBody(schools);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDetailDTO>> getByID(@PathVariable UUID id) {
        SchoolDetailDTO school = schoolService.getById(id);
        APIResponse<SchoolDetailDTO> response = new APIResponse<>();
        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    public ResponseEntity<APIResponse<SchoolDetailDTO>> createSchool(@RequestBody @Valid SchoolDetailDTO school) {
        school = schoolService.create(school);
        APIResponse<SchoolDetailDTO> response = new APIResponse<>();

        URI location = uriFactory.buildFromCurrentRequestWithUuid(school.getId());

        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDetailDTO>> updateSchool(@PathVariable UUID id, @RequestBody @Valid SchoolDetailDTO school) {
        school = schoolService.update(id, school);
        APIResponse<SchoolDetailDTO> response = new APIResponse<>();
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
