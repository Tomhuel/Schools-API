package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.SchoolEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.SchoolDTO;
import crosso.workshop.schools_api.repository.SchoolRepository;
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
class SchoolServiceTest {

    @InjectMocks
    SchoolService schoolService;

    @Mock
    SchoolRepository schoolRepository;

    @Mock
    ModelMapper mapper;

    @Test
    void getNothing() {
        Mockito.when(schoolRepository.findAll()).thenReturn(Collections.emptyList());
        List<SchoolDTO> result = schoolService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getAll() {
        SchoolEntity entity1 = Mockito.mock(SchoolEntity.class);
        SchoolDTO dto1 = Mockito.mock(SchoolDTO.class);

        Mockito.when(schoolRepository.findAll()).thenReturn(List.of(entity1, entity1));
        Mockito.when(mapper.map(Mockito.any(SchoolEntity.class), Mockito.any())).thenReturn(dto1);

        List<SchoolDTO> result = schoolService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getExistingSchool() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        SchoolEntity entity = Mockito.mock(SchoolEntity.class);
        SchoolDTO dto = Mockito.mock(SchoolDTO.class);

        Mockito.when(schoolRepository.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.map(entity, SchoolDTO.class)).thenReturn(dto);

        SchoolDTO result = schoolService.getById(uuid);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void getNonExistingSchool() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(schoolRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            schoolService.getById(uuid);
        });
    }

    @Test
    void createSchool() {
        SchoolDTO schoolDTO = Mockito.mock(SchoolDTO.class);
        SchoolEntity schoolEntity = Mockito.mock(SchoolEntity.class);
        SchoolEntity databaseEntity = Mockito.mock(SchoolEntity.class);

        UUID uuid = UUID.randomUUID();

        Mockito.when(schoolDTO.getId()).thenReturn(uuid);

        Mockito.when(mapper.map(schoolDTO, SchoolEntity.class)).thenReturn(schoolEntity);
        Mockito.when(mapper.map(schoolEntity, SchoolDTO.class)).thenReturn(schoolDTO);
        Mockito.when(mapper.map(databaseEntity, SchoolDTO.class)).thenReturn(schoolDTO);

        Mockito.when(schoolRepository.save(schoolEntity)).thenReturn(databaseEntity);

        SchoolDTO result = schoolService.create(schoolDTO);

        Assertions.assertNotNull(result);
        Assertions.assertSame(schoolDTO, result);
    }

    @Test
    void updateExistentSchool() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        SchoolDTO dtoGiven = Mockito.mock(SchoolDTO.class);
        SchoolEntity entity = Mockito.mock(SchoolEntity.class);
        SchoolEntity entityUpdated = Mockito.mock(SchoolEntity.class);
        SchoolDTO dtoToGet = Mockito.mock(SchoolDTO.class);

        Mockito.when(entity.getId()).thenReturn(uuid);
        Mockito.when(entityUpdated.getId()).thenReturn(uuid);
        Mockito.when(dtoToGet.getId()).thenReturn(uuid);

        Mockito.when(schoolRepository.existsById(uuid)).thenReturn(true);
        Mockito.when(mapper.map(dtoGiven, SchoolEntity.class)).thenReturn(entity);
        Mockito.when(schoolRepository.save(entity)).thenReturn(entityUpdated);
        Mockito.when(mapper.map(entityUpdated, SchoolDTO.class)).thenReturn(dtoToGet);

        SchoolDTO result = schoolService.update(uuid, dtoGiven);

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(result, dtoGiven);
        Assertions.assertEquals(dtoToGet, result);
    }

    @Test
    void updateNonExistentSchool() {
        UUID uuid = UUID.randomUUID();

        SchoolDTO dtoGiven = Mockito.mock(SchoolDTO.class);

        Mockito.when(schoolRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            schoolService.update(uuid, dtoGiven);
        });
    }

    @Test
    void deleteExistent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();
        SchoolDTO dtoToDelete = Mockito.mock(SchoolDTO.class);
        SchoolEntity entityToDelete = Mockito.mock(SchoolEntity.class);

        Mockito.when(schoolRepository.findById(uuid)).thenReturn(Optional.of(entityToDelete));
        Mockito.when(mapper.map(entityToDelete, SchoolDTO.class)).thenReturn(dtoToDelete);
        Mockito.when(mapper.map(dtoToDelete, SchoolEntity.class)).thenReturn(entityToDelete);

        schoolService.delete(uuid);

        Mockito.verify(schoolRepository).delete(entityToDelete);
    }

    @Test
    void deleteNonExistent() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(schoolRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            schoolService.delete(uuid);
        });
    }
}
