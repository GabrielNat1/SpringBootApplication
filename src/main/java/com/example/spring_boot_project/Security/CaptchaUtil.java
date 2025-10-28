package com.example.spring_boot_project.Security;

import cn.apiclub.captcha.Captcha;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class CaptchaUtil {
    public static Captcha createCaptcha(int width, int height){
        return new Captcha.Builder(width, height)
                .addText()
                .addBackground()
                .addNoise()
                .build();
    }

    public static String encodeCaptcha(Captcha c){
        try(ByteArrayOutputStream o = new ByteArrayOutputStream()){
            ImageIO.write(c.getImage(), "jpg", o);
            return Base64.getEncoder().encodeToString(o.toByteArray());
        } catch (IOException e){
            throw new RuntimeException("error encoding Captcha", e);
        }
    }

}
