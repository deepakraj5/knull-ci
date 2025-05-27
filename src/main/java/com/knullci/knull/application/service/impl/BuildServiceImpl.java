package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.command.CreateBuildCommand;
import com.knullci.knull.application.service.BuildExecutor;
import com.knullci.knull.application.service.BuildService;
import com.knullci.knull.domain.factory.BuildFactory;
import com.knullci.knull.infrastructure.persistence.entity.Build;
import com.knullci.knull.infrastructure.persistence.entity.Job;
import com.knullci.knull.infrastructure.persistence.repository.BuildRepository;
import com.knullci.knull.infrastructure.persistence.repository.JobRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildServiceImpl implements BuildService {

    private final BuildRepository buildRepository;
    private final BuildExecutor buildExecutor;
    private final JobRepository jobRepository;

    private final static Integer numberOfExecutor = 1;
    private final static String STATUS_TYPE_ALL = "ALL";

    private final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

    public BuildServiceImpl(BuildRepository buildRepository, BuildExecutor buildExecutor, JobRepository jobRepository) {
        this.buildRepository = buildRepository;
        this.buildExecutor = buildExecutor;
        this.jobRepository = jobRepository;
    }

    @Override
    @SneakyThrows
    public void buildNext() {

        // TODO: check if build executor is busy and if just return

        logger.info("Fetching first " + numberOfExecutor + " queued builds");
        List<Build> builds = this.buildRepository.findFirstNQueuedBuild(numberOfExecutor);

        for (Build build: builds) {
            this.buildExecutor.runBuild(build);
        }
    }

    @Override
    public List<com.knullci.knull.domain.model.Build> getBuilds(String status) {

        logger.info("Fetching builds with status: " + status);

        List<Build> builds;
        if (status.equals(STATUS_TYPE_ALL)) {
            builds = this.buildRepository.findAll(Sort.by("id").descending());
        } else {
            builds = this.buildRepository.findAllByStatus(status, Sort.by("id").descending());
        }

        return builds.stream().map(BuildFactory::fromEntity).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void save(com.knullci.knull.domain.model.Build build) {
        logger.info("Saving the build");
        this.buildRepository.save(build.toEntity());
        logger.info("Saved the build");
    }

    @Override
    @SneakyThrows
    public void createNewBuild(CreateBuildCommand command) {

        logger.info("Creating new build for job id: {}", command.jobId());
        Optional<Job> job = this.jobRepository.findById(command.jobId());
        if (job.isEmpty()) {
            logger.warn("No job found for job id: {}", command.jobId());
            return;
        }

        if (!job.get().isActive()) {
            logger.warn("Job is not active, skipping the request");
            return;
        }

        var build = BuildFactory.fromJob(job.get());
        build.setJobId(job.get().getId());

        this.buildRepository.save(build.toEntity());

        logger.info("New build created for job id: " + job.get().getId());
    }
}
