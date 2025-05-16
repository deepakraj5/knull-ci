package com.knullci.knull.infrastructure.persistence.repository;

import com.knullci.knull.infrastructure.persistence.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Job getJobByName(String name);

    @Query(value = "SELECT * FROM jobs WHERE scm_url = ?1 AND branch = ?2", nativeQuery = true)
    Optional<Job> getJobByScmAndBranch(String scmUrl, String branch);

}
