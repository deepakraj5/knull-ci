package com.knullci.knull.application.service;

import com.knullci.knull.application.command.GitHubWebhookRequestCommand;

public interface GithubWebhookService {
    void createNewBuild(GitHubWebhookRequestCommand command);
}
