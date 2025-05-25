package com.knullci.knull.application.service;

import com.knullci.knull.application.command.CreateSecretCommand;
import com.knullci.knull.domain.model.Secret;

import java.util.List;

public interface SecretService {
    void createSecret(CreateSecretCommand command);
    List<Secret> getAllSecret();
}
