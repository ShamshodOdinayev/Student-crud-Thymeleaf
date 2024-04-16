package uz.students.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.students.entity.AttachEntity;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity,String> {
}
