package crosso.workshop.schools_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.TeacherDTO;
import crosso.workshop.schools_api.service.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TeacherController controller;

    @MockitoBean
    private TeacherService service;

    @MockitoBean
    private URIFactory uriFactory;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getNothing() throws Exception {
        List<TeacherDTO> teachersMock = List.of();

        Mockito.when(service.getAll()).thenReturn(teachersMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teachers").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(0));
    }

    @Test
    void getSomeTeachers() throws Exception {
        TeacherDTO teacher1 = Mockito.mock(TeacherDTO.class);
        TeacherDTO teacher2 = Mockito.mock(TeacherDTO.class);
        List<TeacherDTO> teachersMock = List.of(teacher1, teacher2);

        Mockito.when(service.getAll()).thenReturn(teachersMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teachers").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(2  ));
    }

    @Test
    void getExistentTeacher() throws Exception {
        UUID uuid = UUID.randomUUID();
        TeacherDTO teacher = Mockito.mock(TeacherDTO.class);

        teacher.setId(uuid);
        teacher.setName("John");
        teacher.setLastname("Doe");
        teacher.setAge(30);
        teacher.setAddress("My house 33");
        teacher.setPhone("922645364");
        teacher.setEmail("johndoe@example.com");

        Mockito.when(service.getById(uuid)).thenReturn(teacher);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teachers/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(teacher.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.lastname").value(teacher.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value(teacher.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.phone ").value(teacher.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(teacher.getAge().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(teacher.getAddress()));

    }

    @Test
    void getNonExistentTeacher() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(service.getById(uuid)).thenThrow(new EntityNotFoundException("Teacher", uuid.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/teachers/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createValidTeacher() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John";
        String lastname = "Doe";
        String email = "johndoe@example.com";
        String phone = "922645364";
        Integer age = 30;
        String address = "My house 33";

        TeacherDTO input = new TeacherDTO();
        input.setName(name);
        input.setLastname(lastname);
        input.setPhone(phone);
        input.setEmail(email);
        input.setAge(age);
        input.setAddress(address);

        TeacherDTO output = new TeacherDTO();
        output.setId(id);
        output.setName(name);
        output.setLastname(lastname);
        output.setPhone(phone);
        output.setEmail(email);
        output.setAge(age);
        output.setAddress(address);

        Mockito.when(service.create(Mockito.any(TeacherDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teachers").content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(output.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(output.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.lastname").value(output.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value(output.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.phone ").value(output.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(output.getAge().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(output.getAddress()));
    }

    @Test
    void createNotValidTeacher() throws Exception {
        TeacherDTO teacher1 = new TeacherDTO();
        TeacherDTO teacher2 = new TeacherDTO();
        TeacherDTO teacher3 = new TeacherDTO();
        TeacherDTO teacher4 = new TeacherDTO();
        TeacherDTO teacher5 = new TeacherDTO();
        TeacherDTO teacher6 = new TeacherDTO();
        TeacherDTO teacher7 = new TeacherDTO();
        TeacherDTO teacher8 = new TeacherDTO();
        TeacherDTO teacher9 = new TeacherDTO();
        TeacherDTO teacher10 = new TeacherDTO();
        TeacherDTO teacher11 = new TeacherDTO();
        TeacherDTO teacher12 = new TeacherDTO();
        TeacherDTO teacher13 = new TeacherDTO();
        TeacherDTO teacher14 = new TeacherDTO();
        TeacherDTO teacher15 = new TeacherDTO();
        TeacherDTO teacher16 = new TeacherDTO();
        TeacherDTO teacher17 = new TeacherDTO();
        TeacherDTO teacher18 = new TeacherDTO();
        TeacherDTO teacher19 = new TeacherDTO();
        TeacherDTO teacher20 = new TeacherDTO();
        TeacherDTO teacher21 = new TeacherDTO();
        TeacherDTO teacher22 = new TeacherDTO();

        String name = "John";
        String lastname = "Doe";
        String email = "johndoe@example.com";
        String phone = "922645364";
        Integer age = 30;
        String address = "My house 33";

        // teacher's name null
        teacher1.setLastname(lastname);
        teacher1.setEmail(email);
        teacher1.setPhone(phone);
        teacher1.setAge(age);
        teacher1.setAddress(address);

        // teacher's name blank
        teacher2.setName("");
        teacher2.setLastname(lastname);
        teacher2.setEmail(email);
        teacher2.setPhone(phone);
        teacher2.setAge(age);
        teacher2.setAddress(address);

        // teacher's name trimmed
        teacher3.setName("   John   ");
        teacher3.setLastname(lastname);
        teacher3.setEmail(email);
        teacher3.setPhone(phone);
        teacher3.setAge(age);
        teacher3.setAddress(address);

        // teacher's name contain numbers
        teacher4.setName("J0hn");
        teacher4.setLastname(lastname);
        teacher4.setEmail(email);
        teacher4.setPhone(phone);
        teacher4.setAge(age);
        teacher4.setAddress(address);

        // teacher's name contain special characters
        teacher5.setName("John!");
        teacher5.setLastname(lastname);
        teacher5.setEmail(email);
        teacher5.setPhone(phone);
        teacher5.setAge(age);
        teacher5.setAddress(address);

        // teacher's lastname null
        teacher6.setName(name);
        teacher6.setEmail(email);
        teacher6.setPhone(phone);
        teacher6.setAge(age);
        teacher6.setAddress(address);

        // teacher's lastname blank
        teacher7.setName(name);
        teacher7.setLastname("");
        teacher7.setEmail(email);
        teacher7.setPhone(phone);
        teacher7.setAge(age);
        teacher7.setAddress(address);

        // teacher's lastname trimmed
        teacher8.setName(name);
        teacher8.setLastname("   Doe  ");
        teacher8.setEmail(email);
        teacher8.setPhone(phone);
        teacher8.setAge(age);
        teacher8.setAddress(address);

        // teacher's lastname contain numbers
        teacher9.setName(name);
        teacher9.setLastname("D03");
        teacher9.setEmail(email);
        teacher9.setPhone(phone);
        teacher9.setAge(age);
        teacher9.setAddress(address);

        // teacher's lastname contain special characters
        teacher10.setName(name);
        teacher10.setLastname("Do! E?");
        teacher10.setEmail(email);
        teacher10.setPhone(phone);
        teacher10.setAge(age);
        teacher10.setAddress(address);

        // teacher's email null
        teacher11.setName(name);
        teacher11.setLastname(lastname);
        teacher11.setPhone(phone);
        teacher11.setAge(age);
        teacher11.setAddress(address);

        // teacher's email blank
        teacher12.setName(name);
        teacher12.setLastname(lastname);
        teacher12.setEmail("");
        teacher12.setPhone(phone);
        teacher12.setAge(age);
        teacher12.setAddress(address);

        // teacher's email wrong format
        teacher13.setName(name);
        teacher13.setLastname(lastname);
        teacher13.setEmail("This is obviously a wrong format for a email");
        teacher13.setPhone(phone);
        teacher13.setAge(age);
        teacher13.setAddress(address);

        // teacher's email special wrong format (unhandled by Spring)
        teacher14.setName(name);
        teacher14.setLastname(lastname);
        teacher14.setEmail("johndoe@examplecom");
        teacher14.setPhone(phone);
        teacher14.setAge(age);
        teacher14.setAddress(address);

        // teacher's phone null
        teacher15.setName(name);
        teacher15.setLastname(lastname);
        teacher15.setEmail(email);
        teacher15.setAge(age);
        teacher15.setAddress(address);

        // teacher's phone blank
        teacher16.setName(name);
        teacher16.setLastname(lastname);
        teacher16.setEmail(email);
        teacher16.setPhone("");
        teacher16.setAge(age);
        teacher16.setAddress(address);

        // teacher's phone wrong format
        teacher17.setName(name);
        teacher17.setLastname(lastname);
        teacher17.setEmail(email);
        teacher17.setPhone("+1 922 736 948");
        teacher17.setAge(age);
        teacher17.setAddress(address);

        // teacher's age null
        teacher18.setName(name);
        teacher18.setLastname(lastname);
        teacher18.setEmail(email);
        teacher18.setPhone(phone);
        teacher18.setAddress(address);

        // teacher's age under min

        teacher19.setName(name);
        teacher19.setLastname(lastname);
        teacher19.setEmail(email);
        teacher19.setPhone(phone);
        teacher19.setAge(1);
        teacher19.setAddress(address);

        // teacher's age over max
        teacher20.setName(name);
        teacher20.setLastname(lastname);
        teacher20.setEmail(email);
        teacher20.setPhone(phone);
        teacher20.setAge(90);
        teacher20.setAddress(address);

        // teacher's address null
        teacher21.setName(name);
        teacher21.setLastname(lastname);
        teacher21.setEmail(email);
        teacher21.setPhone(phone);
        teacher21.setAge(age);

        // teacher's address blank
        teacher22.setName(name);
        teacher22.setLastname(lastname);
        teacher22.setEmail(email);
        teacher22.setPhone(phone);
        teacher22.setAge(age);
        teacher22.setAddress("");

        List<TeacherDTO> teachers = List.of(teacher1, teacher2, teacher3, teacher4, teacher5, teacher6, teacher7,
                teacher8, teacher9, teacher10, teacher11, teacher12, teacher13, teacher14, teacher15, teacher16,
                teacher17, teacher18, teacher19, teacher20, teacher21, teacher22);

        for (TeacherDTO teacher : teachers) {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teachers").content(mapper.writeValueAsString(teacher)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Test
    void updateExistentTeacher() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John";
        String lastname = "Doe";
        String email = "johndoe@example.com";
        String phone = "922645364";
        Integer age = 30;
        String address = "My house 33";

        TeacherDTO input = new TeacherDTO();
        input.setName(name);
        input.setLastname(lastname);
        input.setPhone(phone);
        input.setEmail(email);
        input.setAge(age);
        input.setAddress(address);

        TeacherDTO output = new TeacherDTO();
        output.setId(id);
        output.setName(name);
        output.setLastname(lastname);
        output.setPhone(phone);
        output.setEmail(email);
        output.setAge(age);
        output.setAddress(address);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(TeacherDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/teachers/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(output.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(output.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.lastname").value(output.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value(output.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.phone ").value(output.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.age").value(output.getAge().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value(output.getAddress()));
    }

    @Test
    void updateNonExistentTeacher() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "John";
        String lastname = "Doe";
        String email = "johndoe@example.com";
        String phone = "922645364";
        Integer age = 30;
        String address = "My house 33";

        TeacherDTO input = new TeacherDTO();
        input.setName(name);
        input.setLastname(lastname);
        input.setPhone(phone);
        input.setEmail(email);
        input.setAge(age);
        input.setAddress(address);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(TeacherDTO.class))).thenThrow(new EntityNotFoundException("Teacher", id.toString()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/teachers/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteExistentTeacher() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/teachers/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteNotExistent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException("Teacher", id.toString())).when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/teachers/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
