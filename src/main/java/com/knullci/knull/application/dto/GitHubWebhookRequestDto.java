package com.knullci.knull.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubWebhookRequestDto {
    private String ref;
    @JsonProperty(value = "head_commit")
    private GithubWebhookHeadCommit headCommit;
    private GitHubWebhookRepositoryRequestDto repository;
}
