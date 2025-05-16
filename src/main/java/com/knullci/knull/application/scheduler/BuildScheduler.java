package com.knullci.knull.application.scheduler;

import com.knullci.knull.application.service.BuildService;
import com.knullci.knull.infrastructure.persistence.entity.Build;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuildScheduler {

    private final BuildService buildService;

    private final Logger logger = LoggerFactory.getLogger(BuildScheduler.class);

    public BuildScheduler(BuildService buildService) {
        this.buildService = buildService;
    }

    @Scheduled(fixedDelay = 5000)
    @SneakyThrows
    public void scheduleBuilds() {

        logger.info("New scheduler started at: " + LocalDateTime.now());

        this.buildService.buildNext();;

    }

}
