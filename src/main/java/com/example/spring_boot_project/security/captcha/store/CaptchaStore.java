package com.example.spring_boot_project.security.captcha.store;

import com.example.spring_boot_project.security.captcha.CaptchaData;

import java.util.Optional;

public interface CaptchaStore {

    void save(String id, CaptchaData data);

    Optional<CaptchaData> get(String id);

    void remove(String id);
}