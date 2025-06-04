package crosso.workshop.schools_api.repository;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

    @Query(
            value = "SELECT DISTINCT s FROM teachers t JOIN t.courses c JOIN c.students s WHERE t.id = :teacherId",
            countQuery = "SELECT COUNT(DISTINCT s) FROM teachers t JOIN t.courses c JOIN c.students s WHERE t.id = :teacherId"
    )
    Page<StudentEntity> getStudentsByTeacherId(UUID teacherId, Pageable pageable);


}
