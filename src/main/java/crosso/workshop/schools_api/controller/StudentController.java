package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.StudentDetailDTO;
import crosso.workshop.schools_api.model.StudentReducedDTO;
import crosso.workshop.schools_api.service.StudentService;
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
@AllArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @GetMapping()
    public ResponseEntity<APIResponse<List<StudentReducedDTO>>> getAllStudents() {
        List<StudentReducedDTO> students = studentService.getAll();

        APIResponse<List<StudentReducedDTO>> response = new APIResponse<>();
        response.setBody(students);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDetailDTO>> getStudent(@PathVariable UUID id) {
        StudentDetailDTO student = studentService.getById(id);

        APIResponse<StudentDetailDTO> response = new APIResponse<>();
        response.setBody(student);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<APIResponse<StudentDetailDTO>> createStudent(@RequestBody @Valid StudentDetailDTO student) {
        student = studentService.create(student);

        APIResponse<StudentDetailDTO> response = new APIResponse<>();
        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(student);

        URI location = uriFactory.buildFromCurrentRequestWithUuid(student.getId());

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDetailDTO>> updateStudent(@PathVariable UUID id, @RequestBody @Valid StudentDetailDTO student) {
        student = studentService.update(id, student);
        APIResponse<StudentDetailDTO> response = new APIResponse<>();

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
