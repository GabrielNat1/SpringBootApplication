package com.example.spring_boot_project.security.captcha.store;

import com.example.spring_boot_project.security.captcha.CaptchaData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnBean(RedisTemplate.class) // if create on available redis
public class RedisCaptchaStore implements CaptchaStore {

    private static final String PREFIX = "captcha:";
    private final RedisTemplate<String, CaptchaData> redisTemplate;

    public RedisCaptchaStore(RedisTemplate<String, CaptchaData> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String id, CaptchaData data) {
        redisTemplate.opsForValue().set(
                PREFIX + id,
                data,
                5,
                TimeUnit.MINUTES
        );
    }

    @Override
    public Optional<CaptchaData> get(String id) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(PREFIX + id)
        );
    }

    @Override
    public void remove(String id) {
        redisTemplate.delete(PREFIX + id);
    }
}
