package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.model.TeacherDTO;
import crosso.workshop.schools_api.service.TeacherService;
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
@RequestMapping("api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<TeacherDTO>>> getAllTeacher() {
        List<TeacherDTO> teacherEntities = teacherService.getAll();
        APIResponse<List<TeacherDTO>> response = new APIResponse<>();

        response.setBody(teacherEntities);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDTO>> getTeacher(@PathVariable UUID id) {
        TeacherDTO teacher = teacherService.getById(id);
        APIResponse<TeacherDTO> response = new APIResponse<>();

        response.setBody(teacher);
        response.setUri(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    @PostMapping()
    public ResponseEntity<APIResponse<TeacherDTO>> createTeacher(@RequestBody @Valid TeacherDTO teacher) {
        teacher = teacherService.create(teacher);
        APIResponse<TeacherDTO> response = new APIResponse<>();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(teacher.getId()).toUri();

        response.setUri(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<TeacherDTO>> updateTeacher(@PathVariable UUID id, @RequestBody @Valid TeacherDTO teacher) {
        teacher = teacherService.update(id, teacher);
        APIResponse<TeacherDTO> response = new APIResponse<>();

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