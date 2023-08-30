package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSchool;
    @Column(name = "name_school")
    private String nameSchool;
    @Column(name = "address_school")
    private String addressSchool;
    @Column(name = "phone_number_school")
    private String phoneNumberSchool;
    @Column(name = "email_school")
    private String emailSchool;
    @Column(name = "avatar_school")
    private String avatarSchool;

    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JsonIgnore
    private List<Class> classes;

    public School(int idSchool, String nameSchool, String addressSchool, String phoneNumberSchool, String emailSchool, String avatarSchool) {
        this.idSchool = idSchool;
        this.nameSchool = nameSchool;
        this.addressSchool = addressSchool;
        this.phoneNumberSchool = phoneNumberSchool;
        this.emailSchool = emailSchool;
        this.avatarSchool = avatarSchool;
    }
}
