package crosso.workshop.schools_api.repository;

import crosso.workshop.schools_api.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {}
