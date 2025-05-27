package com.knullci.knull.application.command;

import com.knullci.knull.application.dto.CreateJobRequestDto;
import lombok.Getter;

@Getter
public class CreateJobCommand {

    private final String name;
    private final String description;
    private final String scmUrl;
    private final boolean isPrivateRepo;
    private final Integer scmSecretId;
    private final String branch;
    private final String knullFileLocation;
    private final boolean isActive;

    public CreateJobCommand(CreateJobRequestDto request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.scmUrl = request.getScmUrl();
        this.isPrivateRepo = request.isPrivateRepo();
        this.scmSecretId = request.getScmSecretId();
        this.branch = request.getBranch();
        this.knullFileLocation = request.getKnullFileLocation();
        this.isActive = request.isActive();
    }

}
