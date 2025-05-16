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
                command.getScmSecretId(),
                command.getBranch(),
                command.getKnullFileLocation(),
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
                job.getScmSecretId(),
                job.getBranch(),
                job.getKnullFileLocation()
        );
    }

}
