package uz.students.service;

import org.apache.poi.ss.usermodel.Cell;
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
    public void createExcelFile(){
        int rowCounter=0;
        List<StudentDTO> studentDTOList=null;
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Student Data");

            // Add header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Surname", "Birthdate"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Add student data rows
            for (StudentDTO studentDTO : studentDTOList) {

                Row row = sheet.createRow(rowCounter+ 1);

//                for (int j = 0; j < studentDTOList[i].length; j++) {
//                    Cell cell = row.createCell(j);
//                    if (studentDTOList[i][j] instanceof String) {
//                        cell.setCellValue((String) studentDTOList[i][j]);
//                    }
//                }
//                Cell cell= row.createCell(studentDTO.);

                rowCounter++;
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
}
