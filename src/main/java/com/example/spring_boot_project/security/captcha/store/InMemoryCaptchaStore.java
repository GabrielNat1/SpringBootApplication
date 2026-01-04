package com.example.spring_boot_project.security.captcha.store;

import com.example.spring_boot_project.security.captcha.CaptchaData;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Primary // ← Fallback
public class InMemoryCaptchaStore implements CaptchaStore {

    private final ConcurrentHashMap<String, CaptchaData> store = new ConcurrentHashMap<>();

    @Override
    public void save(String id, CaptchaData data) {
        store.put(id, data);
    }

    @Override
    public Optional<CaptchaData> get(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void remove(String id) {
        store.remove(id);
    }
}