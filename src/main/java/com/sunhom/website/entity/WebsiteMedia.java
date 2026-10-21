package com.sunhom.website.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "website_media")
@Getter
@Setter
public class WebsiteMedia {

    @Id
    @GeneratedValue
    @Column(name = "website_media_id")
    private UUID websiteMediaId;

    @Column(name = "website_media_type", nullable = false)
    private String websiteMediaType;

    @Column(name = "page_section", nullable = false)
    private String pageSection;

    @Column(nullable = false)
    private String title;

    private String subtitle;

    @Column(name = "website_media_url", nullable = false)
    private String websiteMediaUrl;

    @Column(name = "alt_text")
    private String altText;

    @Column(nullable = false)
    private Integer position;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
