package uz.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.students.entity.UniversityEntity;
import uz.students.repository.UniversityRepository;

import java.util.List;

@Service
public class UniversityService {
    @Autowired
    private UniversityRepository universityRepository;

    public List<UniversityEntity> getAll() {
        return universityRepository.findAll();
    }

}
