package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parents")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParent;
    private String nameParent;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    @JsonIgnore
    private Student student;

    @Override
    public String toString() {
        return "Parent{" +
                "idParent=" + idParent +
                ", nameParent='" + nameParent + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
