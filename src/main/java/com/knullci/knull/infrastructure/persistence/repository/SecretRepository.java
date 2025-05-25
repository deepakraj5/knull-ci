package com.knullci.knull.infrastructure.persistence.repository;

import com.knullci.knull.infrastructure.persistence.entity.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Integer> {
}
