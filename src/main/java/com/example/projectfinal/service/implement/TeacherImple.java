package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.*;
import com.example.projectfinal.entity.Class;
import com.example.projectfinal.repository.AccountRepository;
import com.example.projectfinal.repository.ClassRepository;
import com.example.projectfinal.repository.SubjectRepository;
import com.example.projectfinal.repository.TeacherRepository;
import com.example.projectfinal.service.FileUploadService;
import com.example.projectfinal.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherImple implements TeacherService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FileUploadService fileUploadService;
    private TeacherRepository teacherRepository;
    private ClassRepository classRepository;
    private SubjectRepository subjectRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public TeacherImple(TeacherRepository teacherRepository, ClassRepository classRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public TeacherDTO getTeacher(int idTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(idTeacher);
        if (teacher.isEmpty()) {
            return null;
        } else {
            TeacherDTO teacherDTO = modelMapper.map(teacher.get(), TeacherDTO.class);
            return teacherDTO;
        }
    }

    @Override
    public TeacherDTO getNewTeacher() {
        TeacherDTO teacherDTO = modelMapper.map(teacherRepository.findTopByOrderByIdTeacherDesc(), TeacherDTO.class);
        return teacherDTO;
    }

    @Override
    public List<TeacherDTO> getAllTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
            teacherDTOs.add(teacherDTO);
        }
        return teacherDTOs;
    }

    @Override
    @Transactional
    public TeacherDTO saveTeacher(String teacher) {
        try {
            // Chuyển teacher (JSON) về dạng object java
            Teacher teacher_old = objectMapper.readValue(teacher, Teacher.class);
            // Lưu object đó xuống database
            Teacher teacher_new = teacherRepository.save(teacher_old);

            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            TeacherDTO teacherDTO = modelMapper.map(teacher_new, TeacherDTO.class);

            // Tạo biến để mã hoá mật khẩu
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            // Thêm account cho teacher
            List<Role> roles = new ArrayList<>();
            Role role = new Role(3, "ROLE_TEACHER");
            roles.add(role);
            Account account = new Account();
            account.setId(0L);
            account.setUsername("TE" + String.valueOf(teacherDTO.getIdTeacher()));
            account.setPassword(bCryptPasswordEncoder.encode("123456"));
            account.setRoles(roles);
            account.setTeacher(teacher_new);
            account.setEnable(true);

            accountRepository.save(account);

            return teacherDTO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public TeacherDTO updateTeacher(String teacher, String idClass, List<Integer> idSubjects, MultipartFile avatar) {
        try{
            // Chuyển về teacher java
            Teacher teacher_old = objectMapper.readValue(teacher, Teacher.class);

            // Kiểm tra xem có idSubject không
            if (idSubjects != null) {
                List<Subject> subjects = new ArrayList<>();
                for (Integer idSubject : idSubjects) {
                    Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);
                    subjects.add(subjectOptional.get());
                }
                // Set lại dữ liệu quan hệ thay đổi
                teacher_old.setSubjects(subjects);
            }

            // Kiểm tra xem có avatar không
            if(!avatar.isEmpty()) {
                // Nếu có update avatar thì set lại avatar
                String avatarURL = fileUploadService.uploadFile(avatar, "teacher_" + teacher_old.getIdTeacher());
                // Set lại dữ liệu quan hệ thay đổi
                teacher_old.setAvatar(avatarURL);
            } else {
                // Nếu không có avatar thì set lại avatar cũ
                Optional<Teacher> tempTeacher = teacherRepository.findById(teacher_old.getIdTeacher());
                teacher_old.setAvatar(tempTeacher.get().getAvatar());
            }

            // Kiểm tra xem có idClass không (-1 làm mặc định là không chọn cái gì hết)
            // Nếu có chọn class
            if (!idClass.equals("-1")) {
                Optional<Teacher> tempTeacher = teacherRepository.findById(teacher_old.getIdTeacher());
                Optional<Class> classOptional = classRepository.findById(Integer.parseInt(idClass));
                // Nếu có class thì set cái class này quan hệ với nhau
                classOptional.get().setTeacher(tempTeacher.get());
                // Set lại dữ liệu quan hệ thay đổi
                teacher_old.setClasss(classOptional.get());
            } else {
                // Nếu không chọn class
                Optional<Teacher> tempTeacher = teacherRepository.findById(teacher_old.getIdTeacher());
                if (tempTeacher.get().getClasss() != null) {
                    Optional<Class> classOptional = classRepository.findById(tempTeacher.get().getClasss().getIdClass());
                    classOptional.get().setTeacher(null);
                    teacher_old.setClasss(null);
                }
            }


            // Lưu object đó xuống database
            Teacher teacher_new = teacherRepository.saveAndFlush(teacher_old);

            // Rồi chuyển object đó thành object DTO để làm thành json đẩy lên client
            TeacherDTO teacherDTO = modelMapper.map(teacher_new, TeacherDTO.class);

            return teacherDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteTeacher(int idTeacher) {
        try{
            Optional<Teacher> teacherOptional = teacherRepository.findById(idTeacher);
            // Xoá account đi trước rồi mới xoá được teacher
            Optional<Account> accountOptional = accountRepository.findByTeacher(teacherOptional.get());
            if (accountOptional.isPresent()) {
                accountRepository.deleteById(accountOptional.get().getId());
            }

            // Kiểm tra xem nếu có class thì set về null
            if (teacherOptional.get().getClasss() != null) {
                Optional<Class> classOptional = classRepository.findById(teacherOptional.get().getClasss().getIdClass());
                classOptional.get().setTeacher(null);
                classRepository.saveAndFlush(classOptional.get());
            }
            // Set subject về null
            teacherOptional.get().setSubjects(null);

            teacherRepository.deleteById(teacherOptional.get().getIdTeacher());
            System.out.println("Xoá rồi mà");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
