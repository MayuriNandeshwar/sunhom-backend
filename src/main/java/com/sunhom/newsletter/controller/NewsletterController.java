package com.sunhom.newsletter.controller;

import com.sunhom.newsletter.dto.NewsletterSubscribeRequest;
import com.sunhom.newsletter.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterService service;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @RequestBody NewsletterSubscribeRequest request) {

        String result = service.subscribe(request);

        if ("ALREADY_SUBSCRIBED".equals(result)) {
            return ResponseEntity.status(409).body(result);
        }

        return ResponseEntity.ok(result);
    }
}