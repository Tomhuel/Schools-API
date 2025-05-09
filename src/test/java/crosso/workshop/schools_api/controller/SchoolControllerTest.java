package crosso.workshop.schools_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.service.SchoolService;
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

import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebMvcTest(SchoolController.class)
class SchoolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SchoolController controller;

    @MockitoBean
    private SchoolService service;

    @MockitoBean
    private URIFactory uriFactory;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getNothing() throws Exception {
        List<SchoolDTO> schoolsMock = List.of();

        Mockito.when(service.getAll()).thenReturn(schoolsMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schools").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(0));
    }

    @Test
    void getSomeSchools() throws Exception {
        SchoolDTO school1 = Mockito.mock(SchoolDTO.class);
        SchoolDTO school2 = Mockito.mock(SchoolDTO.class);
        List<SchoolDTO> schoolsMock = List.of(school1, school2);

        Mockito.when(service.getAll()).thenReturn(schoolsMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schools").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.length()").value(2  ));
    }

    @Test
    void getExistentSchool() throws Exception {
        UUID uuid = UUID.randomUUID();
        SchoolDTO school = Mockito.mock(SchoolDTO.class);

        school.setId(uuid);
        school.setName("Magic maths");
        school.setStartDate(new Date());

        Mockito.when(service.getById(uuid)).thenReturn(school);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schools/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(school.getName()));
    }

    @Test
    void getNonExistentSchool() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(service.getById(uuid)).thenThrow(new EntityNotFoundException("School", uuid.toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schools/" + uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createValidSchool() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        Date startDate = new Date("23/06/2003");

        SchoolDTO input = new SchoolDTO();
        input.setName(name);
        input.setStartDate(startDate);

        SchoolDTO output = new SchoolDTO();
        output.setId(id);
        output.setName(name);
        output.setStartDate(startDate);

        Mockito.when(service.create(Mockito.any(SchoolDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/schools").content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name));
    }

    @Test
    void createNotValidSchool() throws Exception {
        SchoolDTO school1 = new SchoolDTO();
        SchoolDTO school2 = new SchoolDTO();
        SchoolDTO school3 = new SchoolDTO();
        SchoolDTO school4 = new SchoolDTO();
        SchoolDTO school5 = new SchoolDTO();
        SchoolDTO school6 = new SchoolDTO();
        SchoolDTO school7 = new SchoolDTO();
        SchoolDTO school8 = new SchoolDTO();

        Date commonDate = new Date("23/06/2003");

        school1.setName("  trimmed  "); // School 1 - name trimmed
        school1.setStartDate(commonDate);

        school2.setName("N4m3 w1th numb3rs"); // School 2 - name numbers
        school2.setStartDate(commonDate);

        school3.setName("$pec!al charac†ers"); // School 3 - name special characters
        school3.setStartDate(commonDate);

        school4.setName(""); // school 4 - name blank
        school4.setStartDate(commonDate);

        school5.setStartDate(commonDate); // school 5 - name null

        school6.setName("Normal Name"); // school 6 - startDate null

        school7.setName("Normal Name"); // school 7 - startDate older than 200 years old
        school7.setStartDate(new Date("01/01/1234"));

        school8.setName("Normal Name"); // school 7 - startDate older than 200 years old
        school8.setStartDate(new Date("01/01/4025"));


        List<SchoolDTO> schools = List.of(school1, school2, school3, school4, school5, school6, school7, school8);

        for (SchoolDTO school : schools) {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/schools").content(mapper.writeValueAsString(school)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Test
    void updateExistentSchool() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        Date startDate = new Date("23/06/2003");

        SchoolDTO input = new SchoolDTO();
        input.setName(name);
        input.setStartDate(startDate);

        SchoolDTO output = new SchoolDTO();
        output.setId(id);
        output.setName(name);
        output.setStartDate(startDate);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(SchoolDTO.class))).thenReturn(output);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/schools/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value(name));
    }

    @Test
    void updateNonExistentSchool() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Magic maths";
        Date startDate = new Date("23/06/2003");

        SchoolDTO input = new SchoolDTO();
        input.setName(name);
        input.setStartDate(startDate);

        Mockito.when(service.update(Mockito.any(UUID.class), Mockito.any(SchoolDTO.class))).thenThrow(new EntityNotFoundException("School", id.toString()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/schools/" + id).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteExistentSchool() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/schools/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteNotExistent() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException("School", id.toString())).when(service).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/schools/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
