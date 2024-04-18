package uz.students.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uz.students.entity.UniversityEntity;

import java.util.List;

public interface UniversityRepository extends CrudRepository<UniversityEntity, String> {
    @Query("from UniversityEntity where visible=true ")
    List<UniversityEntity> findAll();
}
