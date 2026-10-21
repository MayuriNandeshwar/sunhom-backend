package com.sunhom.newsletter.repository;

import com.sunhom.newsletter.entity.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NewsletterSubscriberRepository
        extends JpaRepository<NewsletterSubscriber, UUID> {

    Optional<NewsletterSubscriber> findByEmail(String email);

    boolean existsByEmail(String email);

}