package com.example.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, length = 191)
    private String username;
    @Column(name = "password", length = 191)
    private String password;
    private Boolean enable;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToOne (fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne (fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
