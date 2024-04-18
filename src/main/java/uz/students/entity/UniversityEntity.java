package uz.students.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "university")
public class UniversityEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
}
