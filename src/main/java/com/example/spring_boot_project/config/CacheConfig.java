package com.example.spring_boot_project.config;

import com.example.spring_boot_project.serializer.RedisCacheGZIPSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class CacheConfig {
    @Bean
    @Primary
    public RedisCacheConfiguration defaultCacheConfig(){
        RedisCacheGZIPSerializer serializerGzip = new RedisCacheGZIPSerializer();

        return RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializerGzip));
    }
}