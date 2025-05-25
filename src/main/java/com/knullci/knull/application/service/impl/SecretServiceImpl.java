package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.command.CreateSecretCommand;
import com.knullci.knull.application.service.SecretService;
import com.knullci.knull.domain.factory.SecretFactory;
import com.knullci.knull.domain.model.Secret;
import com.knullci.knull.infrastructure.persistence.repository.SecretRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretServiceImpl implements SecretService {

    private final SecretRepository secretRepository;

    private final static Logger logger = LoggerFactory.getLogger(SecretServiceImpl.class);

    public SecretServiceImpl(SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    @Override
    @SneakyThrows
    public void createSecret(CreateSecretCommand command) {
        logger.info("Creating new secret");

        Secret secret = SecretFactory.createFromCommand(command);

        this.secretRepository.save(secret.toEntity());

        logger.info("Created new secret");
    }

    @Override
    public List<Secret> getAllSecret() {
        logger.info("Fetching all secrets");

        var secrets = this.secretRepository.findAll();
        return secrets
                .stream()
                .map(SecretFactory::maskSecret)
                .toList();
    }
}
