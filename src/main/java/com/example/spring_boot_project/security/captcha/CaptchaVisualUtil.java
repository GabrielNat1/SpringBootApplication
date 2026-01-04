package com.example.spring_boot_project.security.captcha;

import cn.apiclub.captcha.Captcha;

public class CaptchaVisualUtil {
    public static Captcha strong(int w, int h) {
        return new Captcha.Builder(w, h)
                .addText()
                .addNoise()
                .addBackground()
                .gimp()
                .build();
    }
}
