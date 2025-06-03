package crosso.workshop.schools_api.repository;

import crosso.workshop.schools_api.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID>, QueryByExampleExecutor<CourseEntity> {}
