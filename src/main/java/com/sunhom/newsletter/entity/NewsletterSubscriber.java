package com.sunhom.newsletter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "newsletter_subscribers")
public class NewsletterSubscriber {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "user_id")
    private UUID userId;

    @Column(nullable = false)
    private String source;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}