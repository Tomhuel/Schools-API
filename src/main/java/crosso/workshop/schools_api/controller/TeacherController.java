package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.student.StudentDetailDTO;
import crosso.workshop.schools_api.model.teacher.TeacherDetailDTO;
import crosso.workshop.schools_api.model.teacher.TeacherReducedDTO;
import crosso.workshop.schools_api.service.TeacherService;
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
@RequestMapping("api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final HttpServletRequest httpServletRequest;
    private final URIFactory uriFactory;

    @GetMapping()
    public ResponseEntity<APIResponse<List<TeacherReducedDTO>>> getAllTeacher() {
        List<TeacherReducedDTO> teacherEntities = teacherService.getAll();
        APIResponse<List<TeacherReducedDTO>> response = new APIResponse<>();

        response.setBody(teacherEntities);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<APIResponse<List<StudentDetailDTO>>> getTeacherStudents(@PathVariable UUID id) {
        List<StudentDetailDTO> students = teacherService.getStudentsByTeacherId(id);
        APIResponse<List<StudentDetailDTO>> response = new APIResponse<>();

        response.setBody(students);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDetailDTO>> getTeacher(@PathVariable UUID id) {
        TeacherDetailDTO teacher = teacherService.getById(id);
        APIResponse<TeacherDetailDTO> response = new APIResponse<>();

        response.setBody(teacher);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
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
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDetailDTO>> updateTeacher(@PathVariable UUID id, @RequestBody @Valid TeacherDetailDTO teacher) {
        teacher = teacherService.update(id, teacher);
        APIResponse<TeacherDetailDTO> response = new APIResponse<>();

        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteTeacher(@PathVariable UUID id) {
        teacherService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        
        response.setBody("Teacher deleted successfully");
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}