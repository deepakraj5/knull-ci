package com.knullci.knull.domain.factory;

import com.knullci.knull.domain.model.Build;
import com.knullci.knull.domain.model.enums.BuildStatus;

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

}
