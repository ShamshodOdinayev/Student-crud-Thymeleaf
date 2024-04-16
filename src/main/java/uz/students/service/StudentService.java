package uz.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.entity.StudentEntity;
import uz.students.repository.StudentRepository;
import uz.students.repository.custom.StudentCustomRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentCustomRepository studentCustomRepository;
    @Autowired
    private AttachService attachService;

    public Page<StudentDTO> getProfileList(StudentFilterDTO filterDTO, int page, int size) {
        Page<StudentDTO> result = studentCustomRepository.filter(filterDTO, page - 1, size);
        return result;
    }

    public void create(StudentDTO dto, MultipartFile file) {
        String photoId = null;
        if (!file.isEmpty()) {
            photoId = attachService.save2(file);
        }
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentId(dto.getStudentId());
        studentEntity.setName(dto.getName());
        studentEntity.setSurname(dto.getSurname());
        studentEntity.setGender(dto.getGender());
        studentEntity.setDescription(dto.getDescription());
        studentEntity.setPhotoId(photoId);
        studentEntity.setBirthDate(dto.getBirthDate());
        studentEntity.setMiddleName(dto.getMiddleName());
        studentEntity.setStudyStartDate(dto.getStudyStartDate());
        studentEntity.setStudyEndDate(dto.getStudyEndDate());
        studentEntity.setFieldOfStudy(dto.getFieldOfStudy());
        studentRepository.save(studentEntity);
    }
}
