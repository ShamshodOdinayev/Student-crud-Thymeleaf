package uz.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Page<StudentDTO> getProfileList(StudentFilterDTO filterDTO, int page, int size) {
        return null;
    }
}
