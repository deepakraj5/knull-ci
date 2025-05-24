package com.knullci.knull.domain.model;

import com.knullci.knull.domain.model.enums.BuildStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Build {

    private Integer id;
    private Integer jobId;
    private BuildStatus status;
    private String ref;
    private String headCommit;
    private Integer repositoryId;
    private String repositoryName;
    private String repositoryFullName;
    private String repositoryUrl;
    private String repositoryCloneUrl;
    private String repositoryOwner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Build(BuildStatus status, String ref, String headCommit, Integer repositoryId, String repositoryName, String repositoryFullName, String repositoryUrl, String repositoryCloneUrl, String repositoryOwner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.status = status;
        this.ref = ref;
        this.headCommit = headCommit;
        this.repositoryId = repositoryId;
        this.repositoryName = repositoryName;
        this.repositoryFullName = repositoryFullName;
        this.repositoryUrl = repositoryUrl;
        this.repositoryCloneUrl = repositoryCloneUrl;
        this.repositoryOwner = repositoryOwner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public com.knullci.knull.infrastructure.persistence.entity.Build toEntity() {
        return new com.knullci.knull.infrastructure.persistence.entity.Build(
                this.getId(),
                this.getJobId(),
                this.getStatus().toString(),
                this.getRef(),
                this.getHeadCommit(),
                this.getRepositoryId(),
                this.getRepositoryName(),
                this.getRepositoryFullName(),
                this.getRepositoryUrl(),
                this.getRepositoryCloneUrl(),
                this.getRepositoryOwner(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setStatus(BuildStatus buildStatus) {
        this.status = buildStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
