package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.service.CourseService;
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
@AllArgsConstructor
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @GetMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAll();
        APIResponse<List<CourseDTO>> response = new APIResponse<>();
        response.setBody(courses);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public ResponseEntity<APIResponse<CourseDTO>> getCourse(@PathVariable UUID id) {
        CourseDTO course = courseService.getById(id);
        APIResponse<CourseDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<CourseDTO>> createCourse(@RequestBody @Valid CourseDTO course) {
        course = courseService.create(course);
        APIResponse<CourseDTO> response = new APIResponse<>();

        URI location = uriFactory.buildFromCurrentRequestWithUuid(course.getId());

        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<CourseDTO>> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDTO course) {
        course = courseService.update(id, course);
        APIResponse<CourseDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<APIResponse<String>> deleteCourse(@PathVariable UUID id) {
        courseService.delete(id);

        APIResponse<String> response = new APIResponse<>();
        response.setBody("Course deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
