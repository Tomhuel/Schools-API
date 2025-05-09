package crosso.workshop.schools_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.StudentDTO;
import crosso.workshop.schools_api.service.StudentService;
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

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private StudentController controller;

    @MockitoBean
    private StudentService service;

    @MockitoBean
    private URIFactory uriFactory;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getNothing() throws Exception {
        List<StudentDTO> studentsMock = List.of();

        Mockito.when(service.getAll()).thenReturn(studentsMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(0));
    }

    @Test
    void getSomeStudents() throws Exception {
        StudentDTO student1 = Mockito.mock(StudentDTO.class);
        StudentDTO student2 = Mockito.mock(StudentDTO.class);
        List<StudentDTO> studentsMock = List.of(student1, student2);

        Mockito.when(service.getAll()).thenReturn(studentsMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(2  ));
    }

    @Test
    void getExistentStudent() throws Exception {
        UUID uuid = UUID.randomUUID();
        StudentDTO student = Mockito.mock(StudentDTO.class);

        student.setId(uuid);
        student.setName("John Doe");
        student.setAge(6);
        student.setAddress("My house 33");

        Mockito.when(service.getById(uuid)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(student.getAge().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(student.getAddress()));

    }

    @Test
    void getNonExistentStudent() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(service.getById(uuid)).thenThrow(new EntityNotFoundException("Student", uuid.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createValidStudent() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        Integer age = 6;
        String address = "My house 33";

        StudentDTO input = new StudentDTO();
        input.setName(name);
        input.setAge(age);
        input.setAddress(address);

        StudentDTO output = new StudentDTO();
        output.setId(id);
        output.setName(name);
        output.setAge(age);
        output.setAddress(address);

        Mockito.when(service.create(Mockito.any(StudentDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students").content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(age.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(address));
    }

    @Test
    void createNotValidStudent() throws Exception {
        StudentDTO student1 = new StudentDTO();
        StudentDTO student2 = new StudentDTO();
        StudentDTO student3 = new StudentDTO();
        StudentDTO student4 = new StudentDTO();
        StudentDTO student5 = new StudentDTO();
        StudentDTO student6 = new StudentDTO();
        StudentDTO student7 = new StudentDTO();
        StudentDTO student8 = new StudentDTO();
        StudentDTO student9 = new StudentDTO();
        StudentDTO student10 = new StudentDTO();

        // student's name null
        student1.setAge(6);
        student1.setAddress("Valid address");

        // student's name blank
        student2.setName("");
        student2.setAge(6);
        student2.setAddress("Valid address");

        // student's name trimmed
        student3.setName("   John Does Pasta   ");
        student3.setAge(6);
        student3.setAddress("Valid address");

        // student's name contain numbers
        student4.setName("John 2nd");
        student4.setAge(6);
        student4.setAddress("Valid address");

        // student's name contain special characters
        student5.setName("I'm John!");
        student5.setAge(6);
        student5.setAddress("Valid address");

        // student's age null
        student6.setName("John");
        student6.setAddress("Valid address");

        // student's age over max
        student7.setName("John");
        student7.setAge(20);
        student7.setAddress("Valid address");

        // student's age under max
        student8.setName("John");
        student8.setAge(1);
        student8.setAddress("Valid address");

        // student's address null
        student9.setName("John");
        student9.setAge(6);

        // student's address blank
        student10.setName("John");
        student10.setAge(6);
        student10.setAddress("");


        List<StudentDTO> students = List.of(student1, student2, student3, student4, student5, student6, student7,
                student8, student9, student10);

        for (StudentDTO student : students) {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students").content(mapper.writeValueAsString(student)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Test
    void updateExistentStudent() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        Integer age = 6;
        String address = "My house 33";

        StudentDTO input = new StudentDTO();
        input.setName(name);
        input.setAge(age);
        input.setAddress(address);

        StudentDTO output = new StudentDTO();
        output.setId(id);
        output.setName(name);
        output.setAge(age);
        output.setAddress(address);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(StudentDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(age.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(address));
    }

    @Test
    void updateNonExistentStudent() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        Integer age = 6;
        String address = "My house 33";

        StudentDTO input = new StudentDTO();
        input.setName(name);
        input.setAge(age);
        input.setAddress(address);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(StudentDTO.class))).thenThrow(new EntityNotFoundException("Student", id.toString()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteExistentStudent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteNotExistent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException("Student", id.toString())).when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
