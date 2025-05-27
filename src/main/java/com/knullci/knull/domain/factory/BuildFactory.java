package com.knullci.knull.domain.factory;

import com.knullci.knull.domain.model.Build;
import com.knullci.knull.domain.model.enums.BuildStatus;
import com.knullci.knull.infrastructure.persistence.entity.Job;

import java.time.LocalDateTime;

public class BuildFactory {

    public static Build fromEntity(com.knullci.knull.infrastructure.persistence.entity.Build build) {
        return new Build(
                build.getId(),
                build.getJobId(),
                BuildStatus.valueOf(build.getStatus()),
                build.getRef(),
                build.getHeadCommit(),
                build.getRepositoryId(),
                build.getRepositoryName(),
                build.getRepositoryFullName(),
                build.getRepositoryUrl(),
                build.getRepositoryCloneUrl(),
                build.getRepositoryOwner(),
                build.getCreatedAt(),
                build.getUpdatedAt()
        );
    }

    public static Build fromJob(Job job) {

        String repoFullName = job.getScmUrl().replace("https://github.com/", "").replace(".git", "");
        String repoName = repoFullName.split("/")[1];
        String repoUrl = "https://api.github.com/repos/" + repoFullName;
        String repoOwner = repoFullName.split("/")[0];

        return new Build(
                BuildStatus.QUEUE,
                "refs/heads/" + job.getBranch().toLowerCase(),
                null,
                null,
                repoName,
                repoFullName,
                repoUrl,
                job.getScmUrl(),
                repoOwner,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
