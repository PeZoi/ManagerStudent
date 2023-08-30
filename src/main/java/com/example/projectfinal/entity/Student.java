package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStudent;
    @Column(name = "name_student")
    private String nameStudent;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    private String email;
    private String avatar;

    @ManyToOne (cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_class")
    @JsonIgnore
    private Class classs;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private Parent parent;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReportCard> reportCards;

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", nameStudent='" + nameStudent + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", id_class=" + classs + '\'' +
                '}';
    }
}
