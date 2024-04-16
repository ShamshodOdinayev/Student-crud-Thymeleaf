package uz.students.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.service.AttachService;
import uz.students.util.MapperUtil;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AttachService attachService;

    public Page<StudentDTO> filter(StudentFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder(" where s.visible=true ");
        Map<String, Object> params = new LinkedHashMap<>();

        if (filter.getNameQuery() != null) {
            builder.append(" and (lower(s.name) like :queryParam or lower(s.surname) like :queryParam) ");
            params.put("queryParam", "%" + filter.getNameQuery().toLowerCase() + "%");
        }
        if (filter.getId() != null) {
            builder.append(" and s.student_id = :id ");
            params.put("id", filter.getId());
        }
        // select query
        StringBuilder selectBuilder = new StringBuilder("select s.id, s.student_id, s.middle_name, s.description, s.gender, s.study_start_date, s.study_end_date, s.field_of_study, s.birth_date , s.name, s.surname, s.photo_id, s.created_date");
        selectBuilder.append(" from student as s ");
        // count query
        StringBuilder countBuilder = new StringBuilder("select count(s) from student as s ");

        selectBuilder.append(builder);
        selectBuilder.append(" order by s.created_date desc ");
        countBuilder.append(builder);

        //
        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());
        // set parameters
        // selectQuery.setParameter("lang", appLanguage.name());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        // set pagination
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        // get result
        List<Object[]> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        //
        List<StudentDTO> dtoList = new LinkedList<>();
        for (Object[] object : entityList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setStudentId(MapperUtil.getStringValue(object[1]));
            dto.setMiddleName(MapperUtil.getStringValue(object[2]));
            dto.setDescription(MapperUtil.getStringValue(object[3]));
            dto.setGender(MapperUtil.getGenderValue(object[4]));
            dto.setStudyStartDate(MapperUtil.getLocalDateValue(object[5]));
            dto.setStudyEndDate(MapperUtil.getLocalDateValue(object[6]));
            dto.setFieldOfStudy(MapperUtil.getStringValue(object[7]));
            dto.setBirthDate(MapperUtil.getLocalDateValue(object[8]));
            dto.setName(MapperUtil.getStringValue(object[9]));
            dto.setSurname(MapperUtil.getStringValue(object[10]));
            if (object[11] != null) {
                dto.setPhotoUrl(attachService.getOnlyUrl((String) object[11]));
            }
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[12]));
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, PageRequest.of(page, size), totalElement);
    }
}
