package uz.students.service;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.students.entity.StudentEntity;
import uz.students.repository.StudentRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class PDFService {
    @Autowired
    private StudentRepository studentRepository;

    public void generatePdf(String id) {
        Optional<StudentEntity> studentById = studentRepository.getStudentById(id);

        StudentEntity studentEntity = studentById.get();

        // Create a new document
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();
        // Add content to the document

        Paragraph paragraph = new Paragraph();
        paragraph.add("Student ID: " + studentEntity.getStudentId());
        paragraph.add("\nName: " + studentEntity.getName());
        paragraph.add("\nSurname: " + studentEntity.getSurname());
        paragraph.add("\nDescription: " + studentEntity.getDescription());
        paragraph.add("\nFieldOfStudy: " + studentEntity.getFieldOfStudy());
        paragraph.add("\nStudyStartDate: " + studentEntity.getStudyStartDate());
        paragraph.add("\nStudyEndDate: " + studentEntity.getStudyEndDate());
        paragraph.add("\nPhoto:");

        paragraph.add("\n\n"); // Add some spacing
        document.add(paragraph);

        if (studentEntity.getPhoto() != null && studentEntity.getStudentId() != null) {
            Image img = null;
            try {
                String path = "uploads/" + studentEntity.getPhoto().getPath() + "/" + studentEntity.getPhoto().getId();
                img = Image.getInstance(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            img.scaleAbsolute(100, 100); // Set image dimensions
            document.add(img);
        }

        document.close();

        // Write the PDF content to a file
        String outputPath = "uploadPDF/" + studentEntity.getId() + ".pdf";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            fos.write(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
