package uz.students.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.students.dto.StudentDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private StudentService studentService;

    public void createExcelFile() {
        List<StudentDTO> studentDTOList = studentService.getProfiles();
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Student Data");
            // Add header row
            Row headerRow = sheet.createRow(0);
            List<String> headers = studentService.getColumnNames();

            for (int i = 0; i < headers.size(); i++) {
                headerRow.createCell(i).setCellValue(headers.get(i));
            }
            int rowCounter = 1;
            for (StudentDTO student : studentDTOList) {
                int cell = 0;
                Row row = sheet.createRow(rowCounter++);
                row.createCell(cell++).setCellValue(student.getId());
                row.createCell(cell++).setCellValue(student.getName());
                row.createCell(cell++).setCellValue(student.getSurname());
                row.createCell(cell++).setCellValue(student.getMiddleName());
                row.createCell(cell++).setCellValue(student.getDescription());
                row.createCell(cell++).setCellValue(student.getGender().toString());
                row.createCell(cell++).setCellValue(student.getStudyStartDate());
                row.createCell(cell++).setCellValue(student.getStudyEndDate());
                row.createCell(cell++).setCellValue(student.getFieldOfStudy());
                row.createCell(cell++).setCellValue(student.getBirthDate());
                row.createCell(cell++).setCellValue(student.getPhotoUrl());
                row.createCell(cell++).setCellValue(student.getCreatedDate());
                row.createCell(cell++).setCellValue(student.getUpdatedDate());
                row.createCell(cell++).setCellValue(student.getVisible());
                row.createCell(cell++).setCellValue(student.getDeletedDate());
            }

            // Write the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("student_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeContent(PDPageContentStream contentStream, StudentDTO studentById) {
        try {
//        contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 10);
            contentStream.showText("ID: " + studentById.getStudentId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Name: " + studentById.getName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Surname: " + studentById.getSurname());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Middle name: " + studentById.getMiddleName());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Description: " + studentById.getDescription());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Gender: " + studentById.getGender());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Study start date: " + studentById.getStudyStartDate());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Study End date: " + studentById.getStudyEndDate());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Field of study: " + studentById.getFieldOfStudy());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Birthdate: " + studentById.getBirthDate());
            contentStream.endText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPdf(String id) {
        StudentDTO studentById = studentService.getStudentById(id);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            float imageWidth = 0;
            float imageHeight = 0;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float imageX = (pageWidth - imageWidth) / 2;
            float imageYTop = pageHeight - 100;
            float imageYMiddle = (pageHeight - imageHeight) / 2;
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                if (studentById.getPhotoUrl() != null) {
                    PDImageXObject image = PDImageXObject.createFromFile(studentById.getPhotoUrl(), document);
                    imageWidth = image.getWidth();
                    imageHeight = image.getHeight();
                    // Draw image at the top
//                    contentStream.drawImage(image, imageX, imageYTop, imageWidth, imageHeight);
                    writeContent(contentStream, studentById);


                    // Draw image in the middle
                    contentStream.drawImage(image, imageX, imageYMiddle - imageHeight, imageWidth, imageHeight);
                } else {
                    writeContent(contentStream, studentById);
                }
            }

            document.save("src/main/resources/resume/resume.pdf");
            System.out.println("PDF file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
