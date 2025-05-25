package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.command.CreateSecretCommand;
import com.knullci.knull.application.dto.CreateSecretRequestDto;
import com.knullci.knull.application.service.SecretService;
import com.knullci.knull.domain.model.Secret;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secrets")
@CrossOrigin(origins = "*")
public class SecretController {

    private final SecretService secretService;

    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @PostMapping
    public ResponseEntity<String> createSecret(@RequestBody CreateSecretRequestDto request) {
        this.secretService.createSecret(new CreateSecretCommand(request));

        return ResponseEntity.ok("Created new secret");
    }

    @GetMapping
    public ResponseEntity<List<Secret>> getAllSecret() {
        List<Secret> secrets = this.secretService.getAllSecret();

        return ResponseEntity.ok(secrets);
    }

}
