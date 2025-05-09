package crosso.workshop.schools_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.service.CourseService;
import crosso.workshop.schools_api.utils.response.headers.URIFactory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CourseController controller;

    @MockitoBean
    private CourseService service;

    @MockitoBean
    private URIFactory uriFactory;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getNothing() throws Exception {
        List<CourseDTO> coursesMock = List.of();

        Mockito.when(service.getAll()).thenReturn(coursesMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(0));
    }

    @Test
    void getSomeCourses() throws Exception {
        CourseDTO course1 = Mockito.mock(CourseDTO.class);
        CourseDTO course2 = Mockito.mock(CourseDTO.class);
        List<CourseDTO> coursesMock = List.of(course1, course2);

        Mockito.when(service.getAll()).thenReturn(coursesMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(2  ));
    }

    @Test
    void getExistentCourse() throws Exception {
        UUID uuid = UUID.randomUUID();
        CourseDTO course = Mockito.mock(CourseDTO.class);

        course.setId(uuid);
        course.setCode("MAG1");
        course.setName("Magic maths");
        course.setDescription("Course to learn magic with maths");

        Mockito.when(service.getById(uuid)).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.code").value(course.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(course.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value(course.getDescription()));
    }

    @Test
    void getNonExistentCourse() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(service.getById(uuid)).thenThrow(new EntityNotFoundException("Course", uuid.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courses/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createValidCourse() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        String description = "Course to learn magic with maths";
        String code = "MAG1";

        CourseDTO input = new CourseDTO();
        input.setName(name);
        input.setDescription(description);
        input.setCode(code);

        CourseDTO output = new CourseDTO();
        output.setId(id);
        output.setName(name);
        output.setDescription(description);
        output.setCode(code);

        Mockito.when(service.create(Mockito.any(CourseDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/courses").content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.code").value(code));
    }

    @Test
    void createNotValidCourse() throws Exception {
        CourseDTO course1 = new CourseDTO();
        CourseDTO course2 = new CourseDTO();
        CourseDTO course3 = new CourseDTO();
        CourseDTO course4 = new CourseDTO();
        CourseDTO course5 = new CourseDTO();
        CourseDTO course6 = new CourseDTO();
        CourseDTO course7 = new CourseDTO();
        CourseDTO course8 = new CourseDTO();
        CourseDTO course9 = new CourseDTO();

        course1.setName("  trimmed  "); // Course 1 - name trimmed
        course1.setDescription("");
        course1.setCode("TES6");

        course2.setName("N4m3 w1th numb3rs"); // Course 2 - name numbers
        course2.setDescription("");
        course2.setCode("TES6");

        course3.setName("$pec!al charac†ers"); // Course 3 - name special characters
        course2.setDescription("");
        course3.setCode("TES6");

        course4.setName(""); // course 4 - name blank
        course2.setDescription("");
        course4.setCode("TES6");

        course5.setDescription(""); // course 5 - name null
        course5.setCode("TES5");

        course6.setName("This is a course"); // course 6 - description null
        course6.setCode("TES6");

        course7.setCode("1234"); // course 7 - no pattern course code
        course7.setName("Valid name");
        course7.setDescription("");

        course8.setCode("tes8"); // course 8 - lowercase course code
        course8.setName("Valid name");
        course8.setDescription("");

        course9.setName("Valid name"); // course 9 - course code null
        course9.setDescription("");

        List<CourseDTO> courses = List.of(course1, course2, course3, course4, course5, course6, course7, course8,
                course9);

        for (CourseDTO course : courses) {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/courses").content(mapper.writeValueAsString(course)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Test
    void updateExistentCourse() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        String description = "Course to learn magic with maths";
        String code = "MAG1";

        CourseDTO input = new CourseDTO();
        input.setName(name);
        input.setDescription(description);
        input.setCode(code);

        CourseDTO output = new CourseDTO();
        output.setId(id);
        output.setName(name);
        output.setDescription(description);
        output.setCode(code);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(CourseDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/courses/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.code").value(code));
    }

    @Test
    void updateNonExistentCourse() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        String description = "Course to learn magic with maths";
        String code = "MAG1";

        CourseDTO input = new CourseDTO();
        input.setName(name);
        input.setDescription(description);
        input.setCode(code);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(CourseDTO.class))).thenThrow(new EntityNotFoundException("Course", id.toString()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/courses/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteExistentCourse() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/courses/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteNotExistent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException("Course", id.toString())).when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/courses/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}