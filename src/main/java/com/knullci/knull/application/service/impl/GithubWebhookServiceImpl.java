package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.command.GitHubWebhookRequestCommand;
import com.knullci.knull.application.service.GithubWebhookService;
import com.knullci.knull.domain.factory.GitHubWebhookFactory;
import com.knullci.knull.infrastructure.persistence.entity.Job;
import com.knullci.knull.infrastructure.persistence.repository.BuildRepository;
import com.knullci.knull.infrastructure.persistence.repository.JobRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GithubWebhookServiceImpl implements GithubWebhookService {

    private final BuildRepository buildRepository;
    private final JobRepository jobRepository;

    private final Logger logger = LoggerFactory.getLogger(GithubWebhookServiceImpl.class);

    public GithubWebhookServiceImpl(BuildRepository buildRepository, JobRepository jobRepository) {
        this.buildRepository = buildRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @SneakyThrows
    public void createNewBuild(GitHubWebhookRequestCommand command) {

        logger.info("Creating new build for repository name: " + command.getRepositoryName());

        if (!command.getXGitHubEvent().equals("push")) {
            logger.warn("Ignoring non push event from webhook");
            return;
        }

        String branch = command.getRef().split("/")[2];

        logger.info("Finding scm : " + command.getRepositoryCloneUrl() + " with branch: " + branch);
        Optional<Job> job = this.jobRepository.getJobByScmAndBranch(command.getRepositoryCloneUrl(), branch);

        if (job.isEmpty()) {
            logger.warn("No job found for given request");
            return;
        }

        var build = GitHubWebhookFactory.createFromCommand(command);
        build.setJobId(job.get().getId());

        this.buildRepository.save(build.toEntity());

        logger.info("New build created for job id: " + job.get().getId());
    }
}
