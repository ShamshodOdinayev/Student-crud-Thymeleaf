package uz.students.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "universities")
public class UniversitiesEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
}
