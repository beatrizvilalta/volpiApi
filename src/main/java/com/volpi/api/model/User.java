package com.volpi.api.model;

import com.volpi.api.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "user_auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Post> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Interaction> interactions = new ArrayList<>();

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new java.sql.Timestamp(System.currentTimeMillis());
        updatedAt = new java.sql.Timestamp(System.currentTimeMillis());
    }
}

