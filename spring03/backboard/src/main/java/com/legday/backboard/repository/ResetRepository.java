package com.legday.backboard.repository;

import com.legday.backboard.entity.Reset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetRepository extends JpaRepository<Reset, Integer> {
    Optional<Reset> findByUuid(String uuid);
}
