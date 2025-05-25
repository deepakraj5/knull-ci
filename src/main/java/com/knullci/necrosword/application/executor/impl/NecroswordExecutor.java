package com.knullci.necrosword.application.executor.impl;

import com.knullci.knull.domain.factory.StageFactory;
import com.knullci.knull.domain.model.Stage;
import com.knullci.knull.domain.model.enums.StageStatus;
import com.knullci.knull.infrastructure.persistence.repository.StageRepository;
import com.knullci.necrosword.application.dto.CommandExecutorResult;
import com.knullci.necrosword.application.executor.AbstractShellExecutor;
import com.knullci.necrosword.application.executor.CommandExecutor;
import com.knullci.necrosword.application.model.KnullStage;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class NecroswordExecutor extends AbstractShellExecutor {

    private final CommandExecutor commandExecutor;
    private final StageRepository stageRepository;

    private static final Logger logger = LoggerFactory.getLogger(NecroswordExecutor.class);

    public NecroswordExecutor(CommandExecutor commandExecutor, StageRepository stageRepository) {
        this.commandExecutor = commandExecutor;
        this.stageRepository = stageRepository;
    }

    @Override
    protected List<Stage> saveStages(List<KnullStage> stages, Integer buildId) {
        List<Stage> _stages = stages
                .stream()
                .map(stage -> StageFactory.createFromKnullStage(stage, buildId))
                .toList();

        var stageEntities = this.stageRepository.saveAll(_stages.stream().map(Stage::toEntity).toList());
        return stageEntities
                .stream()
                .map(StageFactory::fromEntity)
                .toList();
    }

    @Override
    @SneakyThrows
    protected void executeStages(String workDir, Integer buildId, List<Stage> stages) {
        for (Stage stage : stages) {
            try {
                logger.info("Executing the stage: {}", stage.getName());

                stage.setStatus(StageStatus.BUILDING);
                this.stageRepository.save(stage.toEntity());

                CommandExecutorResult result = commandExecutor.execute(stage.getCommand(), new File(workDir), buildId, false);

                if (result.exitCode != 0) {
                    logger.error("Build failed for stage: {}", stage.getName());

                    stage.setStatus(StageStatus.FAILED);
                    break;
                }

                stage.setStatus(StageStatus.SUCCESS);
            } catch (Exception exception) {
                logger.error("Build failed for stage: {}, {}", stage.getName(), exception.toString());
                stage.setStatus(StageStatus.FAILED);
            } finally {
                this.stageRepository.save(stage.toEntity());
            }
        }
    }

}
