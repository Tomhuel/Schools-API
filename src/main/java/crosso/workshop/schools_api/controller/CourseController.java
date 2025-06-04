package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.course.*;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.service.CourseService;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Courses")
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @Operation(summary = "Get all courses")
    @GetMapping()
    public ResponseEntity<APIResponse<List<CourseReducedDTO>>> getAllCourses() {
        List<CourseReducedDTO> courses = courseService.getAll();
        APIResponse<List<CourseReducedDTO>> response = new APIResponse<>();
        response.setBody(courses);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Get single course")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDetailDTO>> getCourse(@Parameter(description = "Course's UUID to get") @PathVariable UUID id) {
        CourseDetailDTO course = courseService.getById(id);
        APIResponse<CourseDetailDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Create course")
    @PostMapping()
    public ResponseEntity<APIResponse<CourseDetailDTO>> createCourse(@RequestBody @Valid CourseDetailDTO course) {
        course = courseService.create(course);
        APIResponse<CourseDetailDTO> response = new APIResponse<>();

        URI location = uriFactory.buildFromCurrentRequestWithUuid(course.getId());

        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/search")
    @Operation(summary = "Search courses")
    public ResponseEntity<Page<CourseReducedDTO>> searchCourse(
            @RequestBody CourseSearchDTO courseSearch,
            @Parameter(description = "Sort the objects by {propertyName}") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Type of matching (insensitive case)") @RequestParam(defaultValue =
                    "CONTAINING") String match,
            @Parameter(description = "Sorting order") @RequestParam(defaultValue = "ASC") String direction,
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page's size") @RequestParam(defaultValue = "50") int size
    ) {
        ExampleMatcher.StringMatcher matcher = switch (match.toUpperCase()) {
            case "EXACT" -> ExampleMatcher.StringMatcher.EXACT;
            case "STARTING" -> ExampleMatcher.StringMatcher.STARTING;
            case "ENDING" -> ExampleMatcher.StringMatcher.ENDING;
            default -> ExampleMatcher.StringMatcher.CONTAINING;
        };

        Sort.Direction sortDirection = switch (direction.toUpperCase()) {
            case "DESC" -> Sort.Direction.DESC;
            default -> Sort.Direction.ASC;
        };

        Page<CourseReducedDTO> courses = courseService.searchCourses(courseSearch, sortBy, matcher, sortDirection,
                page, size);

        return ResponseEntity.ok(courses);
    }

    @SneakyThrows
    @Operation(summary = "Update course")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDetailDTO>> updateCourse(@Parameter(description = "Course's ID to update") @PathVariable UUID id, @RequestBody @Valid CourseDetailDTO course) {
        course = courseService.update(id, course);
        APIResponse<CourseDetailDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Delete course")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCourse(@Parameter(description = "Course's ID to update") @PathVariable UUID id) {
        courseService.delete(id);

        APIResponse<String> response = new APIResponse<>();
        response.setBody("Course deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
