package com.knullci.knull.application.service.impl;

import com.knullci.knull.application.service.StageService;
import com.knullci.knull.domain.factory.StageFactory;
import com.knullci.knull.domain.model.Stage;
import com.knullci.knull.infrastructure.persistence.repository.StageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;

    private final static Logger logger = LoggerFactory.getLogger(StageServiceImpl.class);

    public StageServiceImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public List<Stage> getAllStageByBuildId(Integer buildId) {

        logger.info("Fetching stages for the build id: {}", buildId);

        var stages = this.stageRepository.findAllByBuildId(buildId);

        return stages
                .stream()
                .map(StageFactory::fromEntity)
                .toList();
    }
}
