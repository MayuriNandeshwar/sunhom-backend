package com.sunhom.auth.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.auth.entity.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    List<UserSession> findByUser_UserIdAndStatus(UUID userId, String status);

    Optional<UserSession> findByRefreshToken(String refreshToken);
}
