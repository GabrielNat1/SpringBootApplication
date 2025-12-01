# Captcha Security

Captcha is used to prevent automated requests and bots.

## How it works

- The `CaptchaUtil` class generates image-based captchas.
- Captchas are encoded in Base64 and sent to the client.
- The client must solve the captcha to proceed with certain requests.

## Main Class

- `CaptchaUtil`: Generates and encodes captchas.

## Example

```java
Captcha captcha = CaptchaUtil.createCaptcha(200, 50);
String base64Image = CaptchaUtil.encodeCaptcha(captcha);
```
*Creates a captcha image and encodes it to Base64 for sending to the client.*

### Captcha Generation

```java
// CaptchaUtil.java
public static Captcha createCaptcha(int width, int height){
    return new Captcha.Builder(width, height)
            .addText()
            .addBackground()
            .addNoise()
            .build();
}
```
*Generates a captcha image with text, background, and noise.*

### Captcha Encoding

```java
// CaptchaUtil.java
public static String encodeCaptcha(Captcha c){
    try(ByteArrayOutputStream o = new ByteArrayOutputStream()){
        ImageIO.write(c.getImage(), "png", o);
        return Base64.getEncoder().encodeToString(o.toByteArray());
    } catch (IOException e){
        throw new RuntimeException("error encoding Captcha", e);
    }
}
```
*Encodes the captcha image to a Base64 string for easy transmission.*
