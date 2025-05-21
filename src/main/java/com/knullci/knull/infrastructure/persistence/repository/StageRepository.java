package com.knullci.knull.infrastructure.persistence.repository;

import com.knullci.knull.infrastructure.persistence.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
}
