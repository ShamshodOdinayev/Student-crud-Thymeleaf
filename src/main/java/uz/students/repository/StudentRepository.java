package uz.students.repository;

import org.springframework.data.repository.CrudRepository;
import uz.students.entity.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, String> {
}
