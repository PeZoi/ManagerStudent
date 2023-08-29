package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.StudentDTO;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.entity.*;
import com.example.projectfinal.repository.*;
import com.example.projectfinal.service.ExcelUploadService;
import com.example.projectfinal.service.FileUploadService;
import com.example.projectfinal.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentImple implements StudentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FileUploadService fileUploadService;
    private StudentRepository studentRepository;
    private ReportCardRepository reportCardRepository;
    private SubjectRepository subjectRepository;
    private ClassRepository classRepository;
    private ParentRepository parentRepository;
    @Autowired
    private ReportCardDetailRepository reportCardDetailRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ExcelUploadService excelUploadService;
    @Autowired
    private final HttpSession httpSession;

    @Autowired
    public StudentImple(StudentRepository studentRepository, ReportCardRepository reportCardRepository, SubjectRepository subjectRepository, ClassRepository classRepository, ParentRepository parentRepository, HttpSession httpSession) {
        this.studentRepository = studentRepository;
        this.reportCardRepository = reportCardRepository;
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.parentRepository = parentRepository;
        this.httpSession = httpSession;
    }

    @Override
    public StudentDTO getStudent(int idStudent) {
        Optional<Student> student = studentRepository.findById(idStudent);
        if (student.isEmpty()) {
            return null;
        } else {
            StudentDTO studentDTO = modelMapper.map(student.get(), StudentDTO.class);
            return studentDTO;
        }
    }

    @Override
    public StudentDTO getNewStudent() {
        StudentDTO studentDTO = modelMapper.map(studentRepository.findTopByOrderByIdStudentDesc(), StudentDTO.class);
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
            studentDTOs.add(studentDTO);
        }
        return studentDTOs;
    }

    @Override
    @Transactional
    public StudentDTO saveStudent(String student) {
        try {
            // Chuyển student (JSON) về dạng object java
            Student student_old = objectMapper.readValue(student, Student.class);
            // Lưu object đó xuống database
            Student student_new = studentRepository.save(student_old);
            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            StudentDTO studentDTO = modelMapper.map(student_new, StudentDTO.class);

            // Vì Student mặc định lúc nào cũng phải có tất cả các bảng điểm của các môn
            List<Subject> subjects = subjectRepository.findAll();
            for (Subject subject : subjects) {
                ReportCard reportCard = new ReportCard();
                reportCard.setStudent(student_new);
                reportCard.setSubject(subject);
                ReportCard reportCardSave = reportCardRepository.save(reportCard);
                // Khi có report card rồi thì phải có điểm mặc định
                for (int i = 1; i <= 6; i++) {
                    ReportCardDetail reportCardDetail = new ReportCardDetail();
                    if (i == 1) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 2) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 3) {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 4) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK2");
                    } else if (i == 5) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK2");
                    } else {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK2");
                    }
                    reportCardDetail.setReportCard(reportCardSave);
                    reportCardDetailRepository.save(reportCardDetail);
                }
            }

            // Tạo biến để mã hoá mật khẩu
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            // Thêm account cho student
            List<Role> roles = new ArrayList<>();
            Role role = new Role(2, "ROLE_USER");
            roles.add(role);
            Account account = new Account();
            account.setId(0L);
            account.setUsername("ST" + String.valueOf(studentDTO.getIdStudent()));
            account.setPassword(bCryptPasswordEncoder.encode("123456"));
            account.setRoles(roles);
            account.setStudent(student_new);
            account.setEnable(true);

            accountRepository.save(account);

            return studentDTO;
        } catch (Exception e) {
            return null;
        }
    }

    // Method này giống save ở chức năng ở trên nhưng giành cho giáo viên save
    // Khác chỗ là student này sẽ có class luôn còn ở trên thì không có class
    @Override
    @Transactional
    public StudentDTO saveStudentTE(String student, int idClass) {
        try {
            // Chuyển student (JSON) về dạng object java
            Student student_old = objectMapper.readValue(student, Student.class);

            // Thêm class vào student
            Optional<Class> classOptional = classRepository.findById(idClass);
            student_old.setClasss(classOptional.get());

            // Lưu object đó xuống database
            Student student_new = studentRepository.save(student_old);

            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            StudentDTO studentDTO = modelMapper.map(student_new, StudentDTO.class);

            // Vì Student mặc định lúc nào cũng phải có tất cả các bảng điểm của các môn
            List<Subject> subjects = subjectRepository.findAll();
            for (Subject subject : subjects) {
                ReportCard reportCard = new ReportCard();
                reportCard.setStudent(student_new);
                reportCard.setSubject(subject);
                ReportCard reportCardSave = reportCardRepository.save(reportCard);
                // Khi có report card rồi thì phải có điểm mặc định
                for (int i = 1; i <= 6; i++) {
                    ReportCardDetail reportCardDetail = new ReportCardDetail();
                    if (i == 1) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 2) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 3) {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK1");
                    } else if (i == 4) {
                        reportCardDetail.setNameReport("15");
                        reportCardDetail.setSemester("HK2");
                    } else if (i == 5) {
                        reportCardDetail.setNameReport("45");
                        reportCardDetail.setSemester("HK2");
                    } else {
                        reportCardDetail.setNameReport("Final");
                        reportCardDetail.setSemester("HK2");
                    }
                    reportCardDetail.setReportCard(reportCardSave);
                    reportCardDetailRepository.save(reportCardDetail);
                }
            }

            // Tạo biến để mã hoá mật khẩu
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            // Thêm account cho student
            List<Role> roles = new ArrayList<>();
            Role role = new Role(2, "ROLE_USER");
            roles.add(role);
            Account account = new Account();
            account.setId(0L);
            account.setUsername("ST" + String.valueOf(studentDTO.getIdStudent()));
            account.setPassword(bCryptPasswordEncoder.encode("123456"));
            account.setRoles(roles);
            account.setStudent(student_new);
            account.setEnable(true);

            accountRepository.save(account);

            return studentDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String student, String parent, String idClass, MultipartFile avatar) {
        try {
            // Chuyển về teacher java
            Student student_old = objectMapper.readValue(student, Student.class);
            Parent parent_old = objectMapper.readValue(parent, Parent.class);

            // Kiểm tra xem có avatar không
            if (!avatar.isEmpty()) {
                // Nếu có update avatar thì set lại avatar
                String avatarURL = fileUploadService.uploadFile(avatar, "student_" + student_old.getIdStudent());
                // Set lại dữ liệu quan hệ thay đổi
                student_old.setAvatar(avatarURL);
            } else {
                // Nếu không có avatar thì set lại avatar cũ
                Optional<Student> tempStudent = studentRepository.findById(student_old.getIdStudent());
                student_old.setAvatar(tempStudent.get().getAvatar());
            }

            // Kiểm tra xem có idClass không (-1 làm mặc định là không chọn cái gì hết)
            // Nếu có chọn class
            if (!idClass.equals("-1")) {
                Optional<Class> classOptional = classRepository.findById(Integer.parseInt(idClass));
                // Nếu có class thì set cái class này quan hệ với nhau
                student_old.setClasss(classOptional.get());
            } else {
                // Nếu không chọn class
                student_old.setClasss(null);
            }

            // Update lại parent
            Parent parent_new = parentRepository.save(parent_old);
            parent_new.setStudent(student_old);
            student_old.setParent(parent_new);

            // Lưu object đó xuống database
            Student student_new = studentRepository.saveAndFlush(student_old);

            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            StudentDTO studentDTO = modelMapper.map(student_new, StudentDTO.class);

            return studentDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteStudent(int idStudent) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(idStudent);

            // Xoá account đi trước rồi mới xoá được student
            Optional<Account> accountOptional = accountRepository.findByStudent(studentOptional.get());
            accountRepository.deleteById(accountOptional.get().getId());

            studentRepository.deleteById(idStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StudentDTO> getAllStudentsByClass(Class classs) {
        List<Student> students = studentRepository.findStudentsByClasss(classs);
        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
            studentDTOs.add(studentDTO);
        }
        return studentDTOs;
    }

    // Chỉ nhiều sinh viên chỉ dành cho giáo viên
    @Override
    public List<StudentDTO> saveStudentDataFromExcel(MultipartFile file) {
        // Tạo list student dto để trả về
        List<StudentDTO> studentsDTOs = new ArrayList<>();

        // Kiểm tra xem có phải file excel không
        if (excelUploadService.isValidExcelFile(file)) {
            try {
                List<Student> students = excelUploadService.getStudentsDataFromExcel(file.getInputStream());

                // Lấy thông tin của teacher ở session để chủ yếu lấy class của teacher đó
                Account accountSession = (Account) httpSession.getAttribute("account_session");
                // Lấy class của teacher đó
                Class classs = accountSession.getTeacher().getClasss();

                // Biến json
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();

                // Lưu student
                for (Student student : students) {
                    // Biến student thành json vì saveStudentTE nhận parameter là string
                    String studentJson = gson.toJson(student);

                    StudentDTO studentDTO = saveStudentTE(studentJson, classs.getIdClass());
                    studentsDTOs.add(studentDTO);
                }

                return studentsDTOs;

            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("The file is not a valid excel file!");
            }
        }
        return studentsDTOs;
    }


}
