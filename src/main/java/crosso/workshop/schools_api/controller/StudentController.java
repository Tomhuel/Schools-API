package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.StudentDTO;
import crosso.workshop.schools_api.service.StudentService;
import crosso.workshop.schools_api.utils.response.APIResponse;
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
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<StudentDTO>>> getAllStudents() {
        List<StudentDTO> students = studentService.getAll();

        APIResponse<List<StudentDTO>> response = new APIResponse<>();
        response.setBody(students);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDTO>> getStudent(@PathVariable UUID id) {
        StudentDTO student = studentService.getById(id);

        APIResponse<StudentDTO> response = new APIResponse<>();
        response.setBody(student);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<APIResponse<StudentDTO>> createStudent(@RequestBody @Valid StudentDTO student) {
        student = studentService.create(student);

        APIResponse<StudentDTO> response = new APIResponse<>();
        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDTO>> updateStudent(@PathVariable UUID id, @RequestBody @Valid StudentDTO student) {
        student = studentService.update(id, student);
        APIResponse<StudentDTO> response = new APIResponse<>();

        response.setBody(student);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteStudent(@PathVariable UUID id) {
        studentService.delete(id);
        APIResponse<String> response = new APIResponse<>();

        response.setBody("Student deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
