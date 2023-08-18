package com.example.projectfinal.controller;

import com.example.projectfinal.entity.Account;
import com.example.projectfinal.repository.AccountRepository;
import com.example.projectfinal.repository.ReportCardRepository;
import com.example.projectfinal.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
        return "school";
    }

    @GetMapping("/class")
    public String classPage(Model model) {
        model.addAttribute("listClass", classService.getAllClasses());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        return "class";
    }

    @GetMapping("/subject")
    public String subjectPage(Model model) {
        model.addAttribute("listSubject", subjectService.getAllSubjects());
        return "subject";
    }

    @GetMapping("/teacher")
    public String teacherPage(Model model) {
        model.addAttribute("listTeacher", teacherService.getAllTeacher());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        model.addAttribute("listSubject", subjectService.getAllSubjects());
        return "teacher";
    }

    @GetMapping("/student")
    public String studentPage(Model model) {
        model.addAttribute("listTeacher", teacherService.getAllTeacher());
        model.addAttribute("listSchool", schoolService.getAllSchools());
        model.addAttribute("listStudent", studentService.getAllStudents());
        return "student";
    }

    @GetMapping("/student-information/{idStudent}")
    public String studentInformationPage(Model model, @PathVariable("idStudent") int idStudent, HttpServletRequest request) {
            model.addAttribute("student", studentService.getStudent(idStudent));
            model.addAttribute("reportCards", reportCardService.getReportCardByIdStudent(idStudent));
            return "student-information";
    }
}
