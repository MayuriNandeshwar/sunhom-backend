package com.sunhom.auth.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sunhom.auth.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    @Query("""
                SELECT r.roleName
                FROM UserRole ur
                JOIN ur.role r
                WHERE ur.user.userId = :userId
            """)
    List<String> findRoleNamesByUserId(UUID userId);
}
