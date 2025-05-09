package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.StudentDTO;
import crosso.workshop.schools_api.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ModelMapper mapper;

    @Test
    void getNothing() {
        Mockito.when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        List<StudentDTO> result = studentService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getAll() {
        StudentEntity entity1 = Mockito.mock(StudentEntity.class);
        StudentDTO dto1 = Mockito.mock(StudentDTO.class);

        Mockito.when(studentRepository.findAll()).thenReturn(List.of(entity1, entity1));
        Mockito.when(mapper.map(Mockito.any(StudentEntity.class), Mockito.any())).thenReturn(dto1);

        List<StudentDTO> result = studentService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getExistingStudent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        StudentEntity entity = Mockito.mock(StudentEntity.class);
        StudentDTO dto = Mockito.mock(StudentDTO.class);

        Mockito.when(studentRepository.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.map(entity, StudentDTO.class)).thenReturn(dto);

        StudentDTO result = studentService.getById(uuid);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void getNonExistingStudent() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(studentRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            studentService.getById(uuid);
        });
    }

    @Test
    void createStudent() {
        StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
        StudentEntity studentEntity = Mockito.mock(StudentEntity.class);
        StudentEntity databaseEntity = Mockito.mock(StudentEntity.class);

        UUID uuid = UUID.randomUUID();

        Mockito.when(studentDTO.getId()).thenReturn(uuid);

        Mockito.when(mapper.map(studentDTO, StudentEntity.class)).thenReturn(studentEntity);
        Mockito.when(mapper.map(studentEntity, StudentDTO.class)).thenReturn(studentDTO);
        Mockito.when(mapper.map(databaseEntity, StudentDTO.class)).thenReturn(studentDTO);

        Mockito.when(studentRepository.save(studentEntity)).thenReturn(databaseEntity);

        StudentDTO result = studentService.create(studentDTO);

        Assertions.assertNotNull(result);
        Assertions.assertSame(studentDTO, result);
    }

    @Test
    void updateExistentStudent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        StudentDTO dtoGiven = Mockito.mock(StudentDTO.class);
        StudentEntity entity = Mockito.mock(StudentEntity.class);
        StudentEntity entityUpdated = Mockito.mock(StudentEntity.class);
        StudentDTO dtoToGet = Mockito.mock(StudentDTO.class);

        Mockito.when(entity.getId()).thenReturn(uuid);
        Mockito.when(entityUpdated.getId()).thenReturn(uuid);
        Mockito.when(dtoToGet.getId()).thenReturn(uuid);

        Mockito.when(studentRepository.existsById(uuid)).thenReturn(true);
        Mockito.when(mapper.map(dtoGiven, StudentEntity.class)).thenReturn(entity);
        Mockito.when(studentRepository.save(entity)).thenReturn(entityUpdated);
        Mockito.when(mapper.map(entityUpdated, StudentDTO.class)).thenReturn(dtoToGet);

        StudentDTO result = studentService.update(uuid, dtoGiven);

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(result, dtoGiven);
        Assertions.assertEquals(dtoToGet, result);
    }

    @Test
    void updateNonExistentStudent() {
        UUID uuid = UUID.randomUUID();

        StudentDTO dtoGiven = Mockito.mock(StudentDTO.class);

        Mockito.when(studentRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            studentService.update(uuid, dtoGiven);
        });
    }

    @Test
    void deleteExistent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();
        StudentDTO dtoToDelete = Mockito.mock(StudentDTO.class);
        StudentEntity entityToDelete = Mockito.mock(StudentEntity.class);

        Mockito.when(studentRepository.findById(uuid)).thenReturn(Optional.of(entityToDelete));
        Mockito.when(mapper.map(entityToDelete, StudentDTO.class)).thenReturn(dtoToDelete);
        Mockito.when(mapper.map(dtoToDelete, StudentEntity.class)).thenReturn(entityToDelete);

        studentService.delete(uuid);

        Mockito.verify(studentRepository).delete(entityToDelete);
    }

    @Test
    void deleteNonExistent() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(studentRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            studentService.delete(uuid);
        });
    }
}
