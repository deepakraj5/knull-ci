package com.knullci.knull.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class Secret {

    private Integer id;
    private String name;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Secret(String name, String value, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public com.knullci.knull.infrastructure.persistence.entity.Secret toEntity() {
        return new com.knullci.knull.infrastructure.persistence.entity.Secret(
                this.id,
                this.name,
                this.value,
                this.createdAt,
                this.updatedAt
        );
    }
}
