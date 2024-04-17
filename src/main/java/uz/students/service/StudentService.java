package uz.students.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.entity.StudentEntity;
import uz.students.enums.Gender;
import uz.students.exp.AppBadException;
import uz.students.repository.StudentRepository;
import uz.students.repository.custom.StudentCustomRepository;

import java.util.Optional;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
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
    public List<StudentDTO> getProfiles() {
        List<StudentDTO> list=new LinkedList<>();
        Iterable<StudentEntity> all = studentRepository.findAll();
        for (StudentEntity studentEntity : all) {
            StudentDTO studentDTO=new StudentDTO();
            studentDTO.setId(studentEntity.getId());
            studentDTO.setStudentId(studentEntity.getStudentId());
            studentDTO.setName(studentEntity.getName());
            studentDTO.setSurname(studentEntity.getSurname());
            studentDTO.setMiddleName(studentEntity.getMiddleName());
            studentDTO.setDescription(studentEntity.getDescription());
            studentDTO.setGender(Gender.MALE);
            studentDTO.setStudyStartDate(studentEntity.getStudyStartDate());
            studentDTO.setStudyEndDate(studentEntity.getStudyEndDate());
            studentDTO.setFieldOfStudy(studentEntity.getFieldOfStudy());
            studentDTO.setBirthDate(studentEntity.getBirthDate());
//            studentEntity.getPhoto().getPath().isEmpty()?studentDTO.setPhotoUrl("Rasm yo'q"):studentDTO.setPhotoUrl(studentEntity.getPhoto().getPath());
//            studentDTO.setPhotoUrl(studentEntity.getPhoto().getPath());
            studentDTO.setCreatedDate(studentEntity.getCreatedDate());
            studentDTO.setUpdatedDate(studentEntity.getUpdatedDate());
            studentDTO.setVisible(studentEntity.getVisible());
            studentDTO.setDeletedDate(studentEntity.getDeletedDate());
            list.add(studentDTO);
        }
        return list;
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

    @SneakyThrows
    public List<String> getColumnNames() {
        List<String> columnNames=new LinkedList<>();
        List<String> headersName=studentRepository.getMetaData();
        if (headersName.isEmpty()) {
            throw new AppBadException(new String("table not found"));
        }
        System.out.println(headersName);
//        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = 0;
        columnCount = headersName.size();
        // The column count starts from 1
        for (int i = 0; i < columnCount; i++ ) {
            String name = headersName.get(i);
            columnNames.add(name);
        }
        return columnNames;
    }
    public StudentDTO getStudentById(String id){
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        StudentDTO studentDTO=new StudentDTO();
        studentDTO.setId(studentEntity.get().getId());
        studentDTO.setName(studentEntity.get().getName());
        studentDTO.setSurname(studentEntity.get().getSurname());
        studentDTO.setMiddleName(studentEntity.get().getMiddleName());
        studentDTO.setDescription(studentEntity.get().getDescription());
        studentDTO.setGender(Gender.MALE);
        studentDTO.setStudyStartDate(studentEntity.get().getStudyStartDate());
        studentDTO.setStudyEndDate(studentEntity.get().getStudyEndDate());
        studentDTO.setFieldOfStudy(studentEntity.get().getFieldOfStudy());
        studentDTO.setBirthDate(studentEntity.get().getBirthDate());
//            studentEntity.getPhoto().getPath().isEmpty()?studentDTO.setPhotoUrl("Rasm yo'q"):studentDTO.setPhotoUrl(studentEntity.getPhoto().getPath());
//            studentDTO.setPhotoUrl(studentEntity.getPhoto().getPath());
        studentDTO.setCreatedDate(studentEntity.get().getCreatedDate());
        studentDTO.setUpdatedDate(studentEntity.get().getUpdatedDate());
        studentDTO.setVisible(studentEntity.get().getVisible());
        studentDTO.setDeletedDate(studentEntity.get().getDeletedDate());
        return studentDTO;
    }

    public StudentDTO getStudentById(String id) {
        Optional<StudentEntity> optional = studentRepository.getStudentById(id);
        return toDTO(optional.get());
    }

    private StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
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
        studentDTO.setFieldOfStudy(studentEntity.getFieldOfStudy());
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
        studentEntity.setFieldOfStudy(studentDTO.getFieldOfStudy());
        studentRepository.save(studentEntity);
    }

    public void delete(String id) {
        studentRepository.deleteById(id);
    }
}
