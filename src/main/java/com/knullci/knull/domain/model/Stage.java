package com.knullci.knull.domain.model;

import com.knullci.knull.domain.model.enums.StageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class Stage {

    private Integer id;
    private Integer buildId;
    private String name;
    private StageStatus status;
    private String command;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Stage(Integer buildId, String name, StageStatus status, String command,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.buildId = buildId;
        this.name = name;
        this.status = status;
        this.command = command;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public com.knullci.knull.infrastructure.persistence.entity.Stage toEntity() {
        return new com.knullci.knull.infrastructure.persistence.entity.Stage(
                this.id,
                this.buildId,
                this.name,
                this.command,
                this.status.toString(),
                this.createdAt,
                this.updatedAt
        );
    }

    public void setStatus(StageStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
