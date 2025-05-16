package com.knullci.knull.infrastructure.persistence.repository;

import com.knullci.knull.infrastructure.persistence.entity.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository extends JpaRepository<Build, Integer> {

    @Query(value = "SELECT * FROM builds WHERE status = 'QUEUE' LIMIT ?1", nativeQuery = true)
    List<Build> findFirstNQueuedBuild(Integer limit);

    List<Build> findAllByStatus(String status);

}
