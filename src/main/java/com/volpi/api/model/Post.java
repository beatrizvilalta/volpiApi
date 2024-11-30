package com.volpi.api.model;

import com.volpi.api.dto.PostRequest;
import com.volpi.api.model.enums.GradeEnum;
import com.volpi.api.model.enums.SchoolLevelEnum;
import com.volpi.api.model.enums.SubjectEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private SubjectEnum subject;

    @Enumerated(EnumType.STRING)
    private SchoolLevelEnum schoolLevel;

    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void postFromPostRequest(PostRequest postRequest) {
        this.title = postRequest.title();
        this.description = postRequest.description();
        this.subject = SubjectEnum.valueOf(postRequest.subject());
        this.schoolLevel = SchoolLevelEnum.valueOf(postRequest.schoolLevel());
        this.grade = GradeEnum.valueOf(postRequest.grade());
    }
}
