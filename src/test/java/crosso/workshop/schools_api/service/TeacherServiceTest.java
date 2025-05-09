package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.TeacherEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.TeacherDTO;
import crosso.workshop.schools_api.repository.TeacherRepository;
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
class TeacherServiceTest {

    @InjectMocks
    TeacherService teacherService;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    ModelMapper mapper;

    @Test
    void getNothing() {
        Mockito.when(teacherRepository.findAll()).thenReturn(Collections.emptyList());
        List<TeacherDTO> result = teacherService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getAll() {
        TeacherEntity entity1 = Mockito.mock(TeacherEntity.class);
        TeacherDTO dto1 = Mockito.mock(TeacherDTO.class);

        Mockito.when(teacherRepository.findAll()).thenReturn(List.of(entity1, entity1));
        Mockito.when(mapper.map(Mockito.any(TeacherEntity.class), Mockito.any())).thenReturn(dto1);

        List<TeacherDTO> result = teacherService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getExistingTeacher() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        TeacherEntity entity = Mockito.mock(TeacherEntity.class);
        TeacherDTO dto = Mockito.mock(TeacherDTO.class);

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.map(entity, TeacherDTO.class)).thenReturn(dto);

        TeacherDTO result = teacherService.getById(uuid);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void getNonExistingTeacher() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            teacherService.getById(uuid);
        });
    }

    @Test
    void createTeacher() {
        TeacherDTO teacherDTO = Mockito.mock(TeacherDTO.class);
        TeacherEntity teacherEntity = Mockito.mock(TeacherEntity.class);
        TeacherEntity databaseEntity = Mockito.mock(TeacherEntity.class);

        UUID uuid = UUID.randomUUID();

        Mockito.when(teacherDTO.getId()).thenReturn(uuid);

        Mockito.when(mapper.map(teacherDTO, TeacherEntity.class)).thenReturn(teacherEntity);
        Mockito.when(mapper.map(teacherEntity, TeacherDTO.class)).thenReturn(teacherDTO);
        Mockito.when(mapper.map(databaseEntity, TeacherDTO.class)).thenReturn(teacherDTO);

        Mockito.when(teacherRepository.save(teacherEntity)).thenReturn(databaseEntity);

        TeacherDTO result = teacherService.create(teacherDTO);

        Assertions.assertNotNull(result);
        Assertions.assertSame(teacherDTO, result);
    }

    @Test
    void updateExistentTeacher() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        TeacherDTO dtoGiven = Mockito.mock(TeacherDTO.class);
        TeacherEntity entity = Mockito.mock(TeacherEntity.class);
        TeacherEntity entityUpdated = Mockito.mock(TeacherEntity.class);
        TeacherDTO dtoToGet = Mockito.mock(TeacherDTO.class);

        Mockito.when(entity.getId()).thenReturn(uuid);
        Mockito.when(entityUpdated.getId()).thenReturn(uuid);
        Mockito.when(dtoToGet.getId()).thenReturn(uuid);

        Mockito.when(teacherRepository.existsById(uuid)).thenReturn(true);
        Mockito.when(mapper.map(dtoGiven, TeacherEntity.class)).thenReturn(entity);
        Mockito.when(teacherRepository.save(entity)).thenReturn(entityUpdated);
        Mockito.when(mapper.map(entityUpdated, TeacherDTO.class)).thenReturn(dtoToGet);

        TeacherDTO result = teacherService.update(uuid, dtoGiven);

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(result, dtoGiven);
        Assertions.assertEquals(dtoToGet, result);
    }

    @Test
    void updateNonExistentTeacher() {
        UUID uuid = UUID.randomUUID();

        TeacherDTO dtoGiven = Mockito.mock(TeacherDTO.class);

        Mockito.when(teacherRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            teacherService.update(uuid, dtoGiven);
        });
    }

    @Test
    void deleteExistent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();
        TeacherDTO dtoToDelete = Mockito.mock(TeacherDTO.class);
        TeacherEntity entityToDelete = Mockito.mock(TeacherEntity.class);

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.of(entityToDelete));
        Mockito.when(mapper.map(entityToDelete, TeacherDTO.class)).thenReturn(dtoToDelete);
        Mockito.when(mapper.map(dtoToDelete, TeacherEntity.class)).thenReturn(entityToDelete);

        teacherService.delete(uuid);

        Mockito.verify(teacherRepository).delete(entityToDelete);
    }

    @Test
    void deleteNonExistent() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(teacherRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            teacherService.delete(uuid);
        });
    }
}
