package uz.students.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.students.enums.Gender;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "study_start_date")
    private LocalDate studyStartDate;

    @Column(name = "study_end_date")
    private LocalDate studyEndDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;


}
