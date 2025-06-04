package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.student.StudentDetailDTO;
import crosso.workshop.schools_api.model.student.StudentReducedDTO;
import crosso.workshop.schools_api.service.StudentService;
import crosso.workshop.schools_api.utils.response.APIResponse;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Students")
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @Operation(summary = "Get all students")
    @GetMapping()
    public ResponseEntity<APIResponse<List<StudentReducedDTO>>> getAllStudents() {
        List<StudentReducedDTO> students = studentService.getAll();

        APIResponse<List<StudentReducedDTO>> response = new APIResponse<>();
        response.setBody(students);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Get single student")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDetailDTO>> getStudent(@Parameter(description = "Student's ID to get") @PathVariable UUID id) {
        StudentDetailDTO student = studentService.getById(id);

        APIResponse<StudentDetailDTO> response = new APIResponse<>();
        response.setBody(student);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Create student")
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
    @Operation(summary = "Update student")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentDetailDTO>> updateStudent(@Parameter(description = "Student's ID to update") @PathVariable UUID id, @RequestBody @Valid StudentDetailDTO student) {
        student = studentService.update(id, student);
        APIResponse<StudentDetailDTO> response = new APIResponse<>();

        response.setBody(student);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Delete student")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteStudent(@Parameter(description = "Student's ID to delete") @PathVariable UUID id) {
        studentService.delete(id);
        APIResponse<String> response = new APIResponse<>();

        response.setBody("Student deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}
