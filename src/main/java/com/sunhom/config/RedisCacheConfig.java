package com.sunhom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisCacheConfig {

        // @Value("${REDIS_HOST:redis}")
        // private String redisHost;

        // @Value("${REDIS_PORT:6379}")
        // private int redisPort;

        // @Bean
        // public RedisConnectionFactory redisConnectionFactory() {
        // RedisStandaloneConfiguration config = new
        // RedisStandaloneConfiguration(redisHost, redisPort);

        // return new LettuceConnectionFactory(config);
        // }

        @Bean
        public ObjectMapper redisCacheObjectMapper() {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                return mapper;
        }

        @Bean
        public CacheManager cacheManager(
                        RedisConnectionFactory connectionFactory,
                        ObjectMapper redisCacheObjectMapper) {

                Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(
                                redisCacheObjectMapper, Object.class);

                RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(10))
                                .disableCachingNullValues()
                                .serializeKeysWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(serializer));

                Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

                cacheConfigurations.put("bestsellers:v2",
                                defaultConfig.entryTtl(Duration.ofMinutes(10)));

                cacheConfigurations.put("products:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(30)));

                cacheConfigurations.put("categories:v1",
                                defaultConfig.entryTtl(Duration.ofHours(1)));

                cacheConfigurations.put("featured:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(15)));

                cacheConfigurations.put("admin-dashboard-summary:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put("admin-dashboard-revenue-analytics:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(10)));

                cacheConfigurations.put("admin-dashboard-order-status:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(10)));

                cacheConfigurations.put("admin:analytics:revenue:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put("admin:analytics:order-status:v1",
                                defaultConfig.entryTtl(Duration.ofMinutes(5)));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .build();
        }
}