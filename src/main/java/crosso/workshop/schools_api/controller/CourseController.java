package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.course.*;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.service.CourseService;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @GetMapping()
    public ResponseEntity<APIResponse<List<CourseReducedDTO>>> getAllCourses() {
        List<CourseReducedDTO> courses = courseService.getAll();
        APIResponse<List<CourseReducedDTO>> response = new APIResponse<>();
        response.setBody(courses);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDetailDTO>> getCourse(@PathVariable UUID id) {
        CourseDetailDTO course = courseService.getById(id);
        APIResponse<CourseDetailDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
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
    public ResponseEntity<APIResponse<List<CourseReducedDTO>>> searchCourse(
            @RequestBody CourseSearchDTO courseSearch,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "CONTAINING") String match,
            @RequestParam(defaultValue = "ASC") String direction
    ) {
        APIResponse<List<CourseReducedDTO>> response = new APIResponse<>();

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

        List<CourseReducedDTO> courses = courseService.searchCourses(courseSearch, sortBy, matcher, sortDirection);

        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(courses);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDetailDTO>> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDetailDTO course) {
        course = courseService.update(id, course);
        APIResponse<CourseDetailDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCourse(@PathVariable UUID id) {
        courseService.delete(id);

        APIResponse<String> response = new APIResponse<>();
        response.setBody("Course deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
