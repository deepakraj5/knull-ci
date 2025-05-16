package com.knullci.knull.interfaces.controller;

import com.knullci.knull.application.command.GitHubWebhookRequestCommand;
import com.knullci.knull.application.dto.GitHubWebhookRequestDto;
import com.knullci.knull.application.service.GithubWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@CrossOrigin(origins = "*")
public class WebhookController {

    private final GithubWebhookService githubWebhookService;

    public WebhookController(GithubWebhookService githubWebhookService) {
        this.githubWebhookService = githubWebhookService;
    }

    @PostMapping("/github")
    public ResponseEntity<String> handleGithubWebhook(@RequestBody GitHubWebhookRequestDto request,
                                                      @RequestHeader(value = "X-GitHub-Event") String xGitHubEvent) {

        this.githubWebhookService.createNewBuild(
                new GitHubWebhookRequestCommand(request, xGitHubEvent)
        );

        return ResponseEntity.ok("success");
    }

}
