package com.knullci.knull.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "scm_url")
    private String scmUrl;

    @Column(name = "is_private_repo")
    private boolean isPrivateRepo;

    @Column(name = "scm_secret_id")
    private Integer scmSecretId;

    @Column(name = "branch")
    private String branch;

    @Column(name = "knull_file_location")
    private String knullFileLocation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

}
