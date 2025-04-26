package crosso.workshop.schools_api.controller;

import crosso.workshop.schools_api.entity.Teacher;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.service.TeacherService;
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
@RequestMapping("api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<APIResponse<List<Teacher>>> getAllTeacher() {
        List<Teacher> teachers = teacherService.getAll();
        APIResponse<List<Teacher>> response = new APIResponse<>();

        response.setBody(teachers);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Teacher>> getTeacher(@PathVariable UUID id) throws EntityNotFoundException {
        Teacher teacher = teacherService.getById(id);
        APIResponse<Teacher> response = new APIResponse<>();

        response.setBody(teacher);
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<APIResponse<Teacher>> createTeacher(@RequestBody Teacher teacher) {
        teacher = teacherService.create(teacher);
        APIResponse<Teacher> response = new APIResponse<>();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(teacher.getId()).toUri();

        response.setURI(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Teacher>> updateTeacher(@PathVariable UUID id, @RequestBody Teacher teacher) throws EntityNotFoundException {
        teacher = teacherService.update(id, teacher);
        APIResponse<Teacher> response = new APIResponse<>();

        response.setURI(httpServletRequest.getRequestURI());
        response.setBody(teacher);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteTeacher(@PathVariable UUID id) throws EntityNotFoundException {
        teacherService.delete(id);
        APIResponse<String> response = new APIResponse<>();
        
        response.setBody("Teacher deleted succesfully");
        response.setURI(httpServletRequest.getRequestURI());

        return ResponseEntity.ok(response);
    }
}