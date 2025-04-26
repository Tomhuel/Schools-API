package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.entity.Course;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<APIResponse<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAll();
        APIResponse<List<Course>> response = new APIResponse<>();
        response.setBody(courses);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Course>> getCourse(@PathVariable UUID id) throws EntityNotFoundException {
        Course course = courseService.getById(id);
        APIResponse<Course> response = new APIResponse<>();
        response.setBody(course);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<APIResponse<Course>> createCourse(@RequestBody Course course) {
        course = courseService.create(course);
        APIResponse<Course> response = new APIResponse<>();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();

        response.setBody(course);
        response.setURI(httpServletRequest.getRequestURI());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Course>> updateCourse(@PathVariable UUID id, @RequestBody Course course) throws EntityNotFoundException {
        course = courseService.update(id, course);
        APIResponse<Course> response = new APIResponse<>();
        response.setBody(course);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCourse(@PathVariable UUID id) throws EntityNotFoundException {
        courseService.delete(id);

        APIResponse<String> response = new APIResponse<>();
        response.setBody("Course deleted succesfully");
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
