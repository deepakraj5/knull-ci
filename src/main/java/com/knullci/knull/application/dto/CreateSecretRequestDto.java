package com.knullci.knull.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateSecretRequestDto {
    private String name;
    private String value;
}
