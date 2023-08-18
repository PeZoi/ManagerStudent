package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTeacher;
    private String nameTeacher;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    private String email;
    private String avatar;

    @OneToOne (mappedBy = "teacher",cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Class classs;

    @ManyToMany (fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_subject", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @JsonIgnore
    private List<Subject> subjects;

    @Override
    public String toString() {
        return "Teacher{" +
                "idTeacher=" + idTeacher +
                ", nameTeacher='" + nameTeacher + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
