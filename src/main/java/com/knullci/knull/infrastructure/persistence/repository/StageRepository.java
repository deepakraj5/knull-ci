package com.knullci.knull.infrastructure.persistence.repository;

import com.knullci.knull.infrastructure.persistence.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    List<Stage> findAllByBuildId(Integer buildId);
}
