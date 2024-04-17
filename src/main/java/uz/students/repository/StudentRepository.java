package uz.students.repository;

import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.students.dto.StudentDTO;
import uz.students.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, String> {
    @Query("from StudentEntity as s where s.id=?1 and s.visible=true ")
    Optional<StudentEntity> getStudentById(String id);

    @Modifying
    @Transactional
    @Query("update StudentEntity s set s.visible=false where s.id=?1")
    void deleteById(String id);
    @Query("from StudentEntity where visible=true ")
    List<StudentEntity> findAll();
    @Query(value = "SELECT column_name\n" +
            "FROM information_schema.columns\n" +
            "WHERE table_schema = 'public' AND table_name = 'student';", nativeQuery = true)
    List<String> getMetaData();
    Optional<StudentEntity> findById(String id);
}
