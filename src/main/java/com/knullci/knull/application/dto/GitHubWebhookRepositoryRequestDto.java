package com.knullci.knull.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubWebhookRepositoryRequestDto {
    private Integer id;
    private String name;
    @JsonProperty(value = "full_name")
    private String fullName;
    private String url;
    @JsonProperty(value = "clone_url")
    private String cloneUrl;
    private GithubWebhookOwner owner;
}
