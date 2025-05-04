package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAll();
        APIResponse<List<CourseDTO>> response = new APIResponse<>();
        response.setBody(courses);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> getCourse(@PathVariable UUID id) {
        CourseDTO course = courseService.getById(id);
        APIResponse<CourseDTO> response = new APIResponse<>();
        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    public ResponseEntity<APIResponse<CourseDTO>> createCourse(@RequestBody @Valid CourseDTO course) {
        course = courseService.create(course);
        APIResponse<CourseDTO> response = new APIResponse<>();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();

        response.setBody(course);
        response.setUri(httpServletRequest.getRequestURI());
        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDTO course) {
        course = courseService.update(id, course);
        APIResponse<CourseDTO> response = new APIResponse<>();
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
