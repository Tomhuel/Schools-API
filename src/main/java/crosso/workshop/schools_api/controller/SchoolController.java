package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.school.SchoolDetailDTO;
import crosso.workshop.schools_api.model.school.SchoolReducedDTO;
import crosso.workshop.schools_api.service.SchoolService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schools")
@Tag(name = "Schools")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @Operation(summary = "Get all schools")
    @GetMapping()
    public ResponseEntity<Page<SchoolReducedDTO>> getAllSchools(
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page's size") @RequestParam(defaultValue = "50") int size
    ) {
        Page<SchoolReducedDTO> schools = schoolService.getAllPaginated(page, size);
        return ResponseEntity.ok(schools);
    }

    @SneakyThrows
    @Operation(summary = "Get single school")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDetailDTO>> getByID(@Parameter(description = "Course's ID to get") @PathVariable UUID id) {
        SchoolDetailDTO school = schoolService.getById(id);
        APIResponse<SchoolDetailDTO> response = new APIResponse<>();
        response.setBody(school);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Create school")
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
    @Operation(summary = "Update school")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SchoolDetailDTO>> updateSchool(@Parameter(description = "School's ID to update") @PathVariable UUID id, @RequestBody @Valid SchoolDetailDTO school) {
        school = schoolService.update(id, school);
        APIResponse<SchoolDetailDTO> response = new APIResponse<>();
        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(school);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Delete school")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteSchool(@Parameter(description = "School's ID to delete") @PathVariable UUID id) {
        schoolService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        response.setBody("School deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
