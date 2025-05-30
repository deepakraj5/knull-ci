package com.knullci.knull.domain.factory;

import com.knullci.knull.application.command.CreateJobCommand;
import com.knullci.knull.application.dto.CreateJobResponseDto;
import com.knullci.knull.domain.model.Job;

import java.time.LocalDateTime;

public class JobFactory {

    public static Job createFromCommand(CreateJobCommand command) {
        return new Job(
                command.getName(),
                command.getDescription(),
                command.getScmUrl(),
                command.isPrivateRepo(),
                command.getScmSecretId(),
                command.getBranch(),
                command.getKnullFileLocation(),
                command.isActive(),
                LocalDateTime.now(),
                1,
                LocalDateTime.now(),
                1
        );
    }

    public static CreateJobResponseDto createJobResponseDto(Job job) {
        return new CreateJobResponseDto(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getScmUrl(),
                job.isPrivateRepo(),
                job.getScmSecretId(),
                job.getBranch(),
                job.getKnullFileLocation(),
                job.isActive()
        );
    }

    public static Job fromEntity(com.knullci.knull.infrastructure.persistence.entity.Job job) {
        return new Job(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getScmUrl(),
                job.isPrivateRepo(),
                job.getScmSecretId(),
                job.getBranch(),
                job.getKnullFileLocation(),
                job.isActive(),
                job.getCreatedAt(),
                job.getCreatedBy(),
                job.getUpdatedAt(),
                job.getUpdatedBy()
        );
    }

}
