package com.example.projectfinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClass;
    @Column(name = "name_class")
    private String nameClass;
    @Column(name = "group_name")
    private String groupName; // khối học
    @Column(name = "school_year")
    private int schoolYear; // niên khoá
    @Column(name = "enrollment")
    private int enrollment; // sĩ số

    @ManyToOne (fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_school")
    @JsonIgnore
    private School school;

    @OneToMany (mappedBy = "classs", cascade = {CascadeType.REFRESH})
    @JsonIgnore
    private List<Student> students;

//    @OneToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @OneToOne (cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "id_teacher")
//    @JsonIgnore
    private Teacher teacher;

    @Override
    public String toString() {
        return "Class{" +
                "idClass=" + idClass +
                ", nameClass='" + nameClass + '\'' +
                ", groupName='" + groupName + '\'' +
                ", schoolYear=" + schoolYear +
                ", enrollment=" + enrollment +
                '}';
    }
}
