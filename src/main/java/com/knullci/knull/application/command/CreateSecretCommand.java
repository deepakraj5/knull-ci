package com.knullci.knull.application.command;

import com.knullci.knull.application.dto.CreateSecretRequestDto;
import lombok.Getter;

@Getter
public class CreateSecretCommand {
    private final String name;
    private final String value;

    public CreateSecretCommand(CreateSecretRequestDto request) {
        this.name = request.getName();
        this.value = request.getValue();
    }
}
