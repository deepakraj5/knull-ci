package com.knullci.knull.domain.factory;

import com.knullci.knull.application.command.GitHubWebhookRequestCommand;
import com.knullci.knull.domain.model.Build;
import com.knullci.knull.domain.model.enums.BuildStatus;

import java.time.LocalDateTime;

public class GitHubWebhookFactory {

    public static Build createFromCommand(GitHubWebhookRequestCommand command) {
        return new Build(
                BuildStatus.QUEUE,
                command.getRef(),
                command.getHeadCommit(),
                command.getRepositoryId(),
                command.getRepositoryName(),
                command.getRepositoryFullName(),
                command.getRepositoryUrl(),
                command.getRepositoryCloneUrl(),
                command.getRepositoryOwner(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
