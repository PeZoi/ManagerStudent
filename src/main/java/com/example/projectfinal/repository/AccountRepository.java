package com.example.projectfinal.repository;

import com.example.projectfinal.entity.Account;
import com.example.projectfinal.entity.Student;
import com.example.projectfinal.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByStudent(Student student);
    public Optional<Account> findByTeacher(Teacher teacher);
    public Optional<Account> findByUsername(String username);
}
