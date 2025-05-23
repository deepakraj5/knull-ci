package com.knullci.knull.domain.factory;

import com.knullci.knull.domain.model.Stage;
import com.knullci.knull.domain.model.enums.StageStatus;
import com.knullci.necrosword.application.model.KnullStage;

import java.time.LocalDateTime;

public class StageFactory {

    public static Stage createFromKnullStage(KnullStage stage, Integer buildId) {
        return new Stage(
                buildId,
                stage.getName(),
                StageStatus.PENDING,
                stage.getCommand(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static Stage fromEntity(com.knullci.knull.infrastructure.persistence.entity.Stage stage) {
        return new Stage(
                stage.getId(),
                stage.getBuildId(),
                stage.getName(),
                StageStatus.valueOf(stage.getStatus()),
                stage.getCommand(),
                stage.getCreatedAt(),
                stage.getUpdatedAt()
        );
    }

}
