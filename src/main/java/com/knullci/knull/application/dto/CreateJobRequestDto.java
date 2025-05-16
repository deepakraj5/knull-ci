package com.knullci.knull.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateJobRequestDto {
    private String name;
    private String description;
    private String scmUrl;
    private Integer scmSecretId;
    private String branch;
    private String knullFileLocation;
}
