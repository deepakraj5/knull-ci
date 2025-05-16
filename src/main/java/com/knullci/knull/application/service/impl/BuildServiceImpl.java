package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.scheduler.BuildScheduler;
import com.knullci.knull.application.service.BuildExecutor;
import com.knullci.knull.application.service.BuildService;
import com.knullci.knull.domain.factory.BuildFactory;
import com.knullci.knull.infrastructure.persistence.entity.Build;
import com.knullci.knull.infrastructure.persistence.repository.BuildRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildServiceImpl implements BuildService {

    private final BuildRepository buildRepository;
    private final BuildExecutor buildExecutor;

    private final Integer numberOfExecutor = 1;

    private final Logger logger = LoggerFactory.getLogger(BuildScheduler.class);

    public BuildServiceImpl(BuildRepository buildRepository, BuildExecutor buildExecutor) {
        this.buildRepository = buildRepository;
        this.buildExecutor = buildExecutor;
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
        var builds = this.buildRepository.findAllByStatus(status);

        return builds.stream().map(BuildFactory::fromEntity).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void save(com.knullci.knull.domain.model.Build build) {
        logger.info("Saving the build");
        this.buildRepository.save(build.toEntity());
        logger.info("Saved the build");
    }
}
