package com.knullci.knull.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "builds")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Build {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "status")
    private String status;

    @Column(name = "ref")
    private String ref;

    @Column(name = "head_commit")
    private String headCommit;

    @Column(name = "repository_id")
    private Integer repositoryId;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "repository_full_name")
    private String repositoryFullName;

    @Column(name = "repository_url")
    private String repositoryUrl;

    @Column(name = "repository_clone_crl")
    private String repositoryCloneUrl;

    @Column(name = "repository_owner")
    private String repositoryOwner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
