package com.knullci.knull.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateJobResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String scmUrl;
    private boolean isPrivateRepo;
    private Integer scmSecretId;
    private String branch;
    private String knullFileLocation;
}
