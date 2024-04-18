package uz.students.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.students.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private String id;
    private String studentId;
    private String name;
    private String surname;
    private String middleName;
    private String description;
    private Gender gender;
    private LocalDate studyStartDate;
    private LocalDate studyEndDate;
    private String fieldOfStudyId;
    private LocalDate birthDate;
    private String photoUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;
    private LocalDateTime deletedDate;
}
