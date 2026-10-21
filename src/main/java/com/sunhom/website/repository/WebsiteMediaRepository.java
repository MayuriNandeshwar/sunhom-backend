package com.sunhom.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunhom.website.entity.WebsiteMedia;

import java.util.List;
import java.util.UUID;

public interface WebsiteMediaRepository
        extends JpaRepository<WebsiteMedia, UUID> {

    List<WebsiteMedia> findByPageSectionAndIsActiveOrderByPositionAsc(
            String pageSection,
            Boolean isActive);
}
