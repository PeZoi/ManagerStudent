package com.example.projectfinal.controller;

import com.example.projectfinal.entity.Account;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ClassService classService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ReportCardService reportCardService;

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/school")
    public String schoolPage(Model model) {
        model.addAttribute("listSchool", schoolService.getAllSchools());
        return "admin/school";
    }

    @GetMapping("/class")
    public String classPage(Model model) {
        model.addAttribute("listClass", classService.getAllClasses());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        return "admin/class";
    }

    @GetMapping("/subject")
    public String subjectPage(Model model) {
        model.addAttribute("listSubject", subjectService.getAllSubjects());
        return "admin/subject";
    }

    @GetMapping("/teacher")
    public String teacherPage(Model model) {
        model.addAttribute("listTeacher", teacherService.getAllTeacher());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        model.addAttribute("listSubject", subjectService.getAllSubjects());
        return "admin/teacher";
    }

    @GetMapping("/student")
    public String studentPage(Model model) {
        model.addAttribute("listTeacher", teacherService.getAllTeacher());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        model.addAttribute("listStudent", studentService.getAllStudents());
        return "admin/student";
    }

    @GetMapping("/student-information/{idStudent}")
    public String studentInformationPage(Model model, @PathVariable("idStudent") int idStudent) {
            model.addAttribute("student", studentService.getStudent(idStudent));
            model.addAttribute("reportCards", reportCardService.getReportCardByIdStudent(idStudent));
            return "student-information";
    }

    @GetMapping("/teacher-information/{idTeacher}")
    public String teacherInformationPage(Model model, @PathVariable("idTeacher") int idTeacher) {
        model.addAttribute("teacher", teacherService.getTeacher(idTeacher));
        return "teacher/teacher-information";
    }

    @GetMapping("/student-te")
    public String studentTEPage(Model model, HttpSession session) {
        try{
            Account sessionObject = (Account) session.getAttribute("account_session");
            // Lấy id class ra
            Class classs = sessionObject.getTeacher().getClasss();
            // Gọi học sinh đang học lớp có id class này
            model.addAttribute("listStudent", studentService.getAllStudentsByClass(classs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "teacher/student-te";
    }
}
