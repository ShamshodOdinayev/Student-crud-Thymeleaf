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

import java.util.Optional;

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
        studentEntity.setFieldOfStudyId(dto.getFieldOfStudyId());
        studentRepository.save(studentEntity);
    }

    public StudentDTO getStudentById(String id) {
        Optional<StudentEntity> optional = studentRepository.getStudentById(id);
        return toDTO(optional.get());
    }

    private StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setSurname(studentEntity.getSurname());
        studentDTO.setMiddleName(studentEntity.getMiddleName());
        if (studentEntity.getPhotoId() != null) {
            studentDTO.setPhotoUrl(attachService.getOnlyUrl(studentEntity.getPhotoId()));
        }
        studentDTO.setBirthDate(studentEntity.getBirthDate());
        studentDTO.setStudyStartDate(studentEntity.getStudyStartDate());
        studentDTO.setStudyEndDate(studentEntity.getStudyEndDate());
//        studentDTO.setFieldOfStudy(studentEntity.getFieldOfStudy());
        studentDTO.setGender(studentEntity.getGender());
        studentDTO.setDescription(studentEntity.getDescription());
        return studentDTO;
    }

    public void update(String studentId, StudentDTO studentDTO, MultipartFile file) {
        Optional<StudentEntity> studentById = studentRepository.getStudentById(studentId);
        StudentEntity studentEntity = studentById.get();
        if (!file.isEmpty()) {
            String photoId = attachService.save2(file);
            studentEntity.setPhotoId(photoId);
        }
        studentEntity.setStudentId(studentDTO.getStudentId());
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setDescription(studentDTO.getDescription());
        studentEntity.setBirthDate(studentDTO.getBirthDate());
        studentEntity.setMiddleName(studentDTO.getMiddleName());
        studentEntity.setStudyStartDate(studentDTO.getStudyStartDate());
        studentEntity.setStudyEndDate(studentDTO.getStudyEndDate());
        studentEntity.setFieldOfStudyId(studentDTO.getFieldOfStudyId());
        studentRepository.save(studentEntity);
    }

    public void delete(String id) {
        studentRepository.deleteById(id);
    }
}
