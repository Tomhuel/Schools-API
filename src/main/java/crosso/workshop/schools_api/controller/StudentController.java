package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.entity.Student;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.service.StudentService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAll();

        APIResponse<List<Student>> response = new APIResponse<>();
        response.setBody(students);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Student>> getStudent(@PathVariable UUID id) throws EntityNotFoundException {
        Student student = studentService.getById(id);

        APIResponse<Student> response = new APIResponse<>();
        response.setBody(student);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<Student>> createStudent(@RequestBody Student student) {
        student = studentService.create(student);

        APIResponse<Student> response = new APIResponse<>();
        response.setURI(httpServletRequest.getRequestURI());
        response.setBody(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Student>> updateStudent(@PathVariable UUID id, @RequestBody Student student) throws EntityNotFoundException {
        student = studentService.update(id, student);
        APIResponse<Student> response = new APIResponse<>();

        response.setBody(student);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteStudent(@PathVariable UUID id) throws EntityNotFoundException {
        studentService.delete(id);
        APIResponse<String> response = new APIResponse<>();

        response.setBody("Student deleted succesfully");
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
