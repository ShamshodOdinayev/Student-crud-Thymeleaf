package uz.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.students.repository.StudentRepository;
import uz.students.service.FileStorageService;
import uz.students.service.ImportExcelService;
import uz.students.service.PDFService;

@Controller
@RequestMapping("/download")
public class FileDownloadController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private PDFService pdfService;
    @Autowired
    private ImportExcelService importExcelService;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        pdfService.generatePdf(fileName);

        // Faylni yuklash uchun xizmatni chaqirish
        Resource resource = fileStorageService.loadFileAsResourcePDF(fileName + ".pdf");

        // Faylni yuklash uchun content type
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadFile() {
        importExcelService.writeToExcel(studentRepository.findAll(), "Entities.xlsx");
        // Faylni yuklash uchun xizmatni chaqirish
        Resource resource = fileStorageService.loadFileAsResourceXlsx("Entities.xlsx");

        // Faylni yuklash uchun content type
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
