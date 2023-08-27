package com.example.projectfinal.service.implement;

import com.example.projectfinal.entity.Student;
import com.example.projectfinal.service.ExcelUploadService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelUploadImple implements ExcelUploadService {
    @Override
    public boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }

    @Override
    public List<Student> getStudentsDataFromExcel(InputStream inputStream) {
        List<Student> students = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // lấy bảng có tên students trong excel
            XSSFSheet sheet = workbook.getSheet("students");

            // Giống như mảng sẽ bằng đầu index là 0
            int rowIndex = 0;
            for (Row row : sheet) {
                // Vì dòng đầu tiên là tiêu đề nên sẽ bỏ qua
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;

                Student student = new Student();

                // Lưu lần dòng vào student
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> student.setNameStudent(cell.getStringCellValue());
                        case 1 -> {
                            // Giả sử bạn đã có cell.getDateCellValue() từ thư viện java.util
                            java.util.Date utilDate = cell.getDateCellValue();
                            // Chuyển đổi từ java.util.Date sang java.sql.Date
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            // Tiếp theo, bạn có thể sử dụng sqlDate như là tham số trong student.setDateOfBirth()
                            student.setDateOfBirth(sqlDate);
                        }
                        case 2 -> student.setPhoneNumber(cell.getStringCellValue());
                        case 3 -> student.setAddress(cell.getStringCellValue());
                        case 4 -> student.setEmail(cell.getStringCellValue());
                        default -> {}
                    }
                    cellIndex++;
                }
                students.add(student);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return students;
    }
}
