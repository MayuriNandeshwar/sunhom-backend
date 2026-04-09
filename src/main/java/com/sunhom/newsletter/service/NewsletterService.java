package com.sunhom.newsletter.service;

import com.sunhom.common.email.EmailService;
import com.sunhom.newsletter.dto.NewsletterSubscribeRequest;
import com.sunhom.newsletter.entity.NewsletterSubscriber;
import com.sunhom.newsletter.repository.NewsletterSubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsletterService {

    private final NewsletterSubscriberRepository repository;
    // private final EmailService emailService;

    public String subscribe(NewsletterSubscribeRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            return "ALREADY_SUBSCRIBED";
        }

        NewsletterSubscriber subscriber = new NewsletterSubscriber();

        subscriber.setId(UUID.randomUUID());
        subscriber.setEmail(request.getEmail());
        subscriber.setSource("footer_newsletter");
        subscriber.setCreatedAt(LocalDateTime.now());

        repository.save(subscriber);
        // emailService.sendNewsletterWelcome(subscriber.getEmail());

        return "SUBSCRIBED";
    }
}