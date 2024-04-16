package uz.students.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
/**
 * hamma Entitylar uchun umumiy fildlar bor class
 * har bir entityda bir xil kod yozmaslik uchun
 */
public class BaseEntity {
    // id UUID buladi data baseda save qilinayotganda uzi generatsiya qiladi
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "visible")
    private Boolean visible = true;
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}
