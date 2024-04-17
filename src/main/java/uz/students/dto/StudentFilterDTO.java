package uz.students.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentFilterDTO {
    private String nameQuery;
    private String id;
    private String pfdUrl;

}
