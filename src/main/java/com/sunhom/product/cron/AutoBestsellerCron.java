package com.sunhom.product.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sunhom.product.service.AutoBestsellerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutoBestsellerCron {

    private final AutoBestsellerService autoBestsellerService;

    @Scheduled(cron = "0 0 2 * * *") // Daily at 2 AM
    public void run() {
        autoBestsellerService.refreshAutoBestsellers(10);
    }
}
