package crosso.workshop.schools_api.service;

import crosso.workshop.schools_api.entity.CourseEntity;
import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.model.CourseDTO;
import crosso.workshop.schools_api.repository.CourseRepository;
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
class CourseServiceTest {

    @InjectMocks
    CourseService courseService;

    @Mock
    CourseRepository courseRepository;

    @Mock
    ModelMapper mapper;

    @Test
    void getNothing() {
        Mockito.when(courseRepository.findAll()).thenReturn(Collections.emptyList());
        List<CourseDTO> result = courseService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getAll() {
        CourseEntity entity1 = Mockito.mock(CourseEntity.class);
        CourseDTO dto1 = Mockito.mock(CourseDTO.class);

        Mockito.when(courseRepository.findAll()).thenReturn(List.of(entity1, entity1));
        Mockito.when(mapper.map(Mockito.any(CourseEntity.class), Mockito.any())).thenReturn(dto1);

        List<CourseDTO> result = courseService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getExistingCourse() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        CourseEntity entity = Mockito.mock(CourseEntity.class);
        CourseDTO dto = Mockito.mock(CourseDTO.class);

        Mockito.when(courseRepository.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.map(entity, CourseDTO.class)).thenReturn(dto);

        CourseDTO result = courseService.getById(uuid);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto, result);
    }

    @Test
    void getNonExistingCourse() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(courseRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            courseService.getById(uuid);
        });
    }

    @Test
    void createCourse() {
        CourseDTO courseDTO = Mockito.mock(CourseDTO.class);
        CourseEntity courseEntity = Mockito.mock(CourseEntity.class);
        CourseEntity databaseEntity = Mockito.mock(CourseEntity.class);

        UUID uuid = UUID.randomUUID();

        Mockito.when(courseDTO.getId()).thenReturn(uuid);

        Mockito.when(mapper.map(courseDTO, CourseEntity.class)).thenReturn(courseEntity);
        Mockito.when(mapper.map(courseEntity, CourseDTO.class)).thenReturn(courseDTO);
        Mockito.when(mapper.map(databaseEntity, CourseDTO.class)).thenReturn(courseDTO);

        Mockito.when(courseRepository.save(courseEntity)).thenReturn(databaseEntity);

        CourseDTO result = courseService.create(courseDTO);

        Assertions.assertNotNull(result);
        Assertions.assertSame(courseDTO, result);
    }

    @Test
    void updateExistentCourse() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();

        CourseDTO dtoGiven = Mockito.mock(CourseDTO.class);
        CourseEntity entity = Mockito.mock(CourseEntity.class);
        CourseEntity entityUpdated = Mockito.mock(CourseEntity.class);
        CourseDTO dtoToGet = Mockito.mock(CourseDTO.class);

        Mockito.when(entity.getId()).thenReturn(uuid);
        Mockito.when(entityUpdated.getId()).thenReturn(uuid);
        Mockito.when(dtoToGet.getId()).thenReturn(uuid);

        Mockito.when(courseRepository.existsById(uuid)).thenReturn(true);
        Mockito.when(mapper.map(dtoGiven, CourseEntity.class)).thenReturn(entity);
        Mockito.when(courseRepository.save(entity)).thenReturn(entityUpdated);
        Mockito.when(mapper.map(entityUpdated, CourseDTO.class)).thenReturn(dtoToGet);

        CourseDTO result = courseService.update(uuid, dtoGiven);

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(result, dtoGiven);
        Assertions.assertEquals(dtoToGet, result);
    }

    @Test
    void updateNonExistentCourse() {
        UUID uuid = UUID.randomUUID();

        CourseDTO dtoGiven = Mockito.mock(CourseDTO.class);

        Mockito.when(courseRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            courseService.update(uuid, dtoGiven);
        });
    }

    @Test
    void deleteExistent() throws EntityNotFoundException {
        UUID uuid = UUID.randomUUID();
        CourseDTO dtoToDelete = Mockito.mock(CourseDTO.class);
        CourseEntity entityToDelete = Mockito.mock(CourseEntity.class);

        Mockito.when(courseRepository.findById(uuid)).thenReturn(Optional.of(entityToDelete));
        Mockito.when(mapper.map(entityToDelete, CourseDTO.class)).thenReturn(dtoToDelete);
        Mockito.when(mapper.map(dtoToDelete, CourseEntity.class)).thenReturn(entityToDelete);

        courseService.delete(uuid);

        Mockito.verify(courseRepository).delete(entityToDelete);
    }

    @Test
    void deleteNonExistent() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(courseRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            courseService.delete(uuid);
        });
    }
}
