package com.example.spring_boot_project.security.captcha;

import cn.apiclub.captcha.Captcha;
import com.example.spring_boot_project.dto.CaptchaResponse;
import com.example.spring_boot_project.security.captcha.store.CaptchaStore;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaptchaService {

    private final CaptchaStore captchaStore;

    public CaptchaService(CaptchaStore captchaStore) {
        this.captchaStore = captchaStore;
    }

    /* ==========================
       GENERATE CAPTCHA
       ========================== */
    public CaptchaResponse generate(String ip, String userAgent) {

        Captcha captcha = new Captcha.Builder(220, 60)
                .addText()
                .addNoise()
                .addBackground()
                .build();

        String captchaId = UUID.randomUUID().toString();
        String answerHash = BCrypt.hashpw(captcha.getAnswer(), BCrypt.gensalt());

        CaptchaData data = new CaptchaData();
        data.setId(captchaId);
        data.setAnswerHash(answerHash);
        data.setIp(ip);
        data.setUserAgent(userAgent);
        data.setAttempts(0);
        data.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        captchaStore.save(captchaId, data);

        return new CaptchaResponse(
                captchaId,
                "data:image/png;base64," + encodeImage(captcha)
        );
    }

    /* ==========================
       VALIDATE CAPTCHA
       ========================== */
    public boolean validate(
            String captchaId,
            String answer,
            String ip,
            String userAgent
    ) {

        Optional<CaptchaData> optional = captchaStore.get(captchaId);

        if (optional.isEmpty()) {
            return false;
        }

        CaptchaData data = optional.get();

        if (data.getExpiresAt().isBefore(LocalDateTime.now())) {
            captchaStore.remove(captchaId);
            return false;
        }

        if (!data.getIp().equals(ip)) return false;
        if (!data.getUserAgent().equals(userAgent)) return false;

        data.setAttempts(data.getAttempts() + 1);

        if (data.getAttempts() > 5) {
            captchaStore.remove(captchaId);
            return false;
        }

        boolean valid = BCrypt.checkpw(answer, data.getAnswerHash());

        if (valid) {
            captchaStore.remove(captchaId);
        } else {
            captchaStore.save(captchaId, data);
        }

        return valid;
    }

    /* ==========================
       UTILS
       ========================== */
    private String encodeImage(Captcha captcha) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(captcha.getImage(), "png", out);
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar captcha", e);
        }
    }
}
