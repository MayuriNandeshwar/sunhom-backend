package com.sunhom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
@EnableScheduling
public class SunhomBackendApplication {

    public static void main(String[] args) {
        sanitizeRedisUrl();
        SpringApplication.run(SunhomBackendApplication.class, args);
    }

    private static void sanitizeRedisUrl() {
        String sysUrl = System.getProperty("spring.data.redis.url");
        if (sysUrl != null && !isValidRedisUrl(sysUrl)) {
            System.setProperty("spring.data.redis.url", "redis://localhost:6379");
            return;
        }

        if (sysUrl == null) {
            String envDataUrl = System.getenv("SPRING_DATA_REDIS_URL");
            String envRedisUrl = System.getenv("SPRING_REDIS_URL");
            String envUrl = System.getenv("REDIS_URL");

            boolean hasInvalidEnv = (envDataUrl != null && !isValidRedisUrl(envDataUrl))
                    || (envRedisUrl != null && !isValidRedisUrl(envRedisUrl))
                    || (envUrl != null && !isValidRedisUrl(envUrl));

            if (hasInvalidEnv) {
                System.setProperty("spring.data.redis.url", "redis://localhost:6379");
            }
        }
    }

    private static boolean isValidRedisUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        String trimmed = url.trim();
        return trimmed.startsWith("redis://") || trimmed.startsWith("rediss://");
    }
}
