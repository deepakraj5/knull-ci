package com.knullci.knull.domain.factory;

import com.knullci.knull.application.command.CreateSecretCommand;
import com.knullci.knull.domain.model.Secret;

import java.time.LocalDateTime;

public class SecretFactory {

    public static Secret createFromCommand(CreateSecretCommand command) {
        return new Secret(
                command.getName(),
                command.getValue(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static Secret maskSecret(com.knullci.knull.infrastructure.persistence.entity.Secret secret) {
        return new Secret(secret.getId(), secret.getName(), "*******", secret.getCreatedAt(), secret.getUpdatedAt());
    }

}
