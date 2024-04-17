package uz.students.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.students.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, String> {
    @Query("from StudentEntity as s where s.id=?1 and s.visible=true ")
    Optional<StudentEntity> getStudentById(String id);

    @Modifying
    @Transactional
    @Query("update StudentEntity s set s.visible=false where s.id=?1")
    void deleteById(String id);
    @Query("from StudentEntity where visible=true ")
    List<StudentEntity> findAll();
}
