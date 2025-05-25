package com.knullci.knull.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Getter
public class Job {

    private Integer id;
    private String name;
    private String description;
    private String scmUrl;
    private boolean isPrivateRepo;
    private Integer scmSecretId;
    private String branch;
    private String knullFileLocation;
    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime updatedAt;
    private Integer updatedBy;

    public Job(String name, String description, String scmUrl, boolean isPrivateRepo, Integer scmSecretId, String branch, String knullFileLocation, LocalDateTime createdAt, Integer createdBy, LocalDateTime updatedAt, Integer updatedBy) {
        this.name = name;
        this.description = description;
        this.scmUrl = scmUrl;
        this.isPrivateRepo = isPrivateRepo;
        this.scmSecretId = scmSecretId;
        this.branch = branch;
        this.knullFileLocation = knullFileLocation;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public com.knullci.knull.infrastructure.persistence.entity.Job toEntity() {
        return new com.knullci.knull.infrastructure.persistence.entity.Job(
                this.id,
                this.name,
                this.description,
                this.scmUrl,
                this.isPrivateRepo,
                this.scmSecretId,
                this.branch,
                this.knullFileLocation,
                this.createdAt,
                this.createdBy,
                this.updatedAt,
                this.updatedBy
        );
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
