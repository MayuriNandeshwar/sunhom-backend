package com.sunhom.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.auth.entity.FailedLoginAttempt;

public interface FailedLoginAttemptRepository
        extends JpaRepository<FailedLoginAttempt, UUID> {
}
