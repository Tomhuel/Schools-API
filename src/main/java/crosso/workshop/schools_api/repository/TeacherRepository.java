package crosso.workshop.schools_api.repository;

import crosso.workshop.schools_api.entity.StudentEntity;
import crosso.workshop.schools_api.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {

    @Query("SELECT DISTINCT s FROM teachers t JOIN t.courses c JOIN c.students s WHERE t.id = :teacherId")
    List<StudentEntity> getStudentsByTeacherId(UUID teacherId);

}
