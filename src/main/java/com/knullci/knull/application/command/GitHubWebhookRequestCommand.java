package com.knullci.knull.application.command;

import com.knullci.knull.application.dto.GitHubWebhookRequestDto;
import lombok.Getter;

@Getter
public class GitHubWebhookRequestCommand {
    private final String xGitHubEvent;
    private final String ref;
    private final String headCommit;
    private final Integer repositoryId;
    private final String repositoryName;
    private final String repositoryFullName;
    private final String repositoryUrl;
    private final String repositoryCloneUrl;
    private final String repositoryOwner;

    public GitHubWebhookRequestCommand(GitHubWebhookRequestDto request, String xGitHubEvent) {
        this.xGitHubEvent = xGitHubEvent;
        this.ref = request.getRef();
        this.headCommit = request.getHeadCommit().getId();
        this.repositoryId = request.getRepository().getId();
        this.repositoryName = request.getRepository().getName();
        this.repositoryFullName = request.getRepository().getFullName();
        this.repositoryUrl = request.getRepository().getUrl();
        this.repositoryCloneUrl = request.getRepository().getCloneUrl();
        this.repositoryOwner = request.getRepository().getOwner().getName();
    }
}
