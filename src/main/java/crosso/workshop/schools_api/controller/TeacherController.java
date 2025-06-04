package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.student.StudentDetailDTO;
import crosso.workshop.schools_api.model.student.StudentReducedDTO;
import crosso.workshop.schools_api.model.teacher.TeacherDetailDTO;
import crosso.workshop.schools_api.model.teacher.TeacherReducedDTO;
import crosso.workshop.schools_api.service.TeacherService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Teachers")
@RequestMapping("api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @Operation(summary = "Get all teachers")
    @GetMapping()
    public ResponseEntity<Page<TeacherReducedDTO>> getAllTeacher(
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page's size") @RequestParam(defaultValue = "50") int size
    ) {
        Page<TeacherReducedDTO> teachers = teacherService.getPaginated(page, size);

        return ResponseEntity.ok(teachers);
    }

    @Operation(summary = "Get all students by a teacher's ID")
    @GetMapping("/{id}/students")
    public ResponseEntity<Page<StudentReducedDTO>> getTeacherStudents(
            @PathVariable UUID id,
            @Parameter(description = "Page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page's size") @RequestParam(defaultValue = "50") int size
    ) {
        Page<StudentReducedDTO> students = teacherService.getStudentsPaginated(id, page, size);

        return ResponseEntity.ok(students);
    }

    @SneakyThrows
    @Operation(summary = "Get single teacher")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDetailDTO>> getTeacher(@PathVariable UUID id) {
        TeacherDetailDTO teacher = teacherService.getById(id);
        APIResponse<TeacherDetailDTO> response = new APIResponse<>();

        response.setBody(teacher);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Create teacher")
    @PostMapping()
    public ResponseEntity<APIResponse<TeacherDetailDTO>> createTeacher(@RequestBody @Valid TeacherDetailDTO teacher) {
        teacher = teacherService.create(teacher);
        APIResponse<TeacherDetailDTO> response = new APIResponse<>();

        URI location = uriFactory.buildFromCurrentRequestWithUuid(teacher.getId());

        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @Operation(summary = "Update teacher")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDetailDTO>> updateTeacher(@PathVariable UUID id, @RequestBody @Valid TeacherDetailDTO teacher) {
        teacher = teacherService.update(id, teacher);
        APIResponse<TeacherDetailDTO> response = new APIResponse<>();

        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @Operation(summary = "Delete teacher")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteTeacher(@PathVariable UUID id) {
        teacherService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        
        response.setBody("Teacher deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}