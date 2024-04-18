package uz.students.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.students.entity.StudentEntity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ImportExcelService {
    public void writeToExcel(List<StudentEntity> entities, String filePath) {
        // Excel faylini yaratish
        try (Workbook workbook = new XSSFWorkbook()) {
            // Yangi Excel sahifasi yaratish
            Sheet sheet = workbook.createSheet("Entities");

            // Sarlavha ustuni yaratish
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Id", "Name", "Surname", "Middle Name", "Birth_date", "Study_start_date", "Study_end_date", "Field_of_study", "Description", "Gender", "Created_date", "Photo_id"}; // Misol uchun
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Ma'lumotlarni joylashtirish
            int rowNum = 1;
            for (StudentEntity entity : entities) {
                Row row = sheet.createRow(rowNum++);
                // Kerakli ustunlarni qoshish

                row.createCell(0).setCellValue(entity.getStudentId());
                row.createCell(1).setCellValue(entity.getName());
                row.createCell(2).setCellValue(entity.getSurname());
                row.createCell(3).setCellValue(entity.getMiddleName());
                row.createCell(4).setCellValue(entity.getBirthDate().toString());
                row.createCell(5).setCellValue(entity.getStudyStartDate().toString());
                row.createCell(6).setCellValue(entity.getStudyEndDate().toString());
                row.createCell(7).setCellValue(entity.getFieldOfStudyId());
                row.createCell(8).setCellValue(entity.getDescription());
                row.createCell(9).setCellValue(entity.getGender() != null ? entity.getGender().toString() : "");
                row.createCell(10).setCellValue(entity.getCreatedDate().toString());
                row.createCell(11).setCellValue(entity.getPhotoId());
            }

            // Faylni saqlash
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
