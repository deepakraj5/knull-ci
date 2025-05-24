package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.service.BuildExecutor;
import com.knullci.knull.application.service.JobService;
import com.knullci.knull.domain.factory.BuildFactory;
import com.knullci.knull.domain.model.enums.BuildStatus;
import com.knullci.knull.domain.model.enums.StageStatus;
import com.knullci.knull.infrastructure.persistence.entity.Build;
import com.knullci.knull.infrastructure.persistence.entity.Job;
import com.knullci.knull.infrastructure.persistence.entity.Stage;
import com.knullci.knull.infrastructure.persistence.repository.BuildRepository;
import com.knullci.knull.infrastructure.persistence.repository.StageRepository;
import com.knullci.necrosword.application.executor.CommandExecutor;
import com.knullci.necrosword.application.executor.impl.NecroswordExecutor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class BuildExecutorImpl implements BuildExecutor {

    private final JobService jobService;
    private final BuildRepository buildRepository;
    private final CommandExecutor commandExecutor;
    private final NecroswordExecutor necroswordExecutor;
    private final StageRepository stageRepository;

    private final Logger logger = LoggerFactory.getLogger(BuildExecutorImpl.class);

    // TODO: get work directory from properties file
    private static final String workspaceDirectory = "/Users/deepakraj/Documents/Deepak/workspace/";

    public BuildExecutorImpl(JobService jobService, BuildRepository buildRepository, CommandExecutor commandExecutor, NecroswordExecutor necroswordExecutor, StageRepository stageRepository) {
        this.jobService = jobService;
        this.buildRepository = buildRepository;
        this.commandExecutor = commandExecutor;
        this.necroswordExecutor = necroswordExecutor;
        this.stageRepository = stageRepository;
    }

    @Override
    @SneakyThrows
    @Async
    public void runBuild(Build _build) {

        Job job = this.jobService.getJobById(_build.getJobId());

        var build = BuildFactory.fromEntity(_build);
        build.setStatus(BuildStatus.BUILDING);
        this.buildRepository.save(build.toEntity());

        String repoName = job.getScmUrl().split("/")[4].replace(".git", "");

        if (Files.isDirectory(Paths.get(workspaceDirectory + repoName))) {
            String gitPullCmd = "git pull";

            commandExecutor.execute(gitPullCmd, new File(workspaceDirectory + repoName));
        } else {
            String gitCloneCmd = "git clone " +
                    job.getScmUrl() + " --branch " +
                    job.getBranch() + " --single-branch";

            commandExecutor.execute(gitCloneCmd, new File(workspaceDirectory));
        }

        String knullFileLocation = workspaceDirectory + repoName + job.getKnullFileLocation();

        necroswordExecutor.execute(knullFileLocation, workspaceDirectory + repoName, build.getId());

        // TODO: update the build status based on the stage status
        List<Stage> stages = this.stageRepository.findAllByBuildId(build.getId());
        List<Stage> nonSuccessfulStages = stages
                .stream()
                .filter(stage -> !Objects.equals(stage.getStatus(), StageStatus.SUCCESS.toString()))
                .toList();

        if (!nonSuccessfulStages.isEmpty()) {
            build.setStatus(BuildStatus.FAILED);
        } else {
            build.setStatus(BuildStatus.SUCCESS);
        }

        this.buildRepository.save(build.toEntity());
    }
}
