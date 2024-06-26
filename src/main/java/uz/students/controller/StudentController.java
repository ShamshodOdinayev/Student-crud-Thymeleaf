package uz.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.students.dto.StudentDTO;
import uz.students.dto.StudentFilterDTO;
import uz.students.entity.UniversityEntity;
import uz.students.service.StudentService;
import uz.students.service.UniversityService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UniversityService universityService;

    @GetMapping("/list")
    public String getProfileList(Model model,
                                 @RequestParam(value = "nameQuery", required = false) String nameQuery,
                                 @RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        StudentFilterDTO filterDTO = correctFilterDTO(nameQuery, id);
        Page<StudentDTO> result = studentService.getProfileList(filterDTO, page, size);
        model.addAttribute("studentList", result.getContent());
        model.addAttribute("totalElements", result.getTotalElements());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("filterDTO", filterDTO);
        model.addAttribute("pageSize", 10);
        return "student/index";
    }

    @GetMapping("/go/add")
    public String goToAdd(Model model) {
        model.addAttribute("student", new StudentDTO());
        List<UniversityEntity> universityList = universityService.getAll();
        model.addAttribute("universityList", universityList);
        model.addAttribute("isUpdate", false);
        return "student/add";
    }

    @PostMapping("/add")
    public String create(@RequestParam("file") MultipartFile file,
                         @ModelAttribute StudentDTO dto,
                         Model model) {
        studentService.create(dto, file);
        return "redirect:/student/list";
    }

    @GetMapping("/go/update/{id}")
    public String updateById(@PathVariable("id") String id, Model model) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        List<UniversityEntity> universityList = universityService.getAll();
        model.addAttribute("universityList", universityList);
        model.addAttribute("student", studentDTO);
        model.addAttribute("isUpdate", true);
        return "student/add";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") String studentId,
                         @RequestParam("file") MultipartFile file,
                         @ModelAttribute StudentDTO studentDTO) {
        studentService.update(studentId, studentDTO, file);
        return "redirect:/student/list";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute StudentDTO dto) {
        studentService.delete(dto.getId());
        return "redirect:/student/list";
    }

    private StudentFilterDTO correctFilterDTO(String nameQuery, String id) {
        StudentFilterDTO filterDTO = new StudentFilterDTO();
        /*
         * search qilinayotganda name yoki surname boyicha qidirayotganini tekshirish
         * */
        if (nameQuery == null || nameQuery.isBlank() || nameQuery.equals("null")) {
            filterDTO.setNameQuery(null);
        } else {
            filterDTO.setNameQuery(nameQuery);
        }
        /*
         * search qilinayotganda id boyicha qidirayotganini tekshirish
         * */
        if (id == null || id.isBlank() || id.equals("null")) {
            filterDTO.setId(null);
        } else {
            filterDTO.setId(id);
        }
        return filterDTO;
    }

}
