package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.CaptchaResponse;
import main.entities.CaptchaCodes;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class CaptchaService {
    @Scheduled(fixedRate = 10000)
    public CaptchaResponse getCaptcha() throws IOException {
        Cage cage = new GCage();
        String code = cage.getTokenGenerator().next();
        String secretCode = UUID.randomUUID().toString();
        OutputStream os = new FileOutputStream("image.png", false);
        try {
            cage.draw(code, os);
        } finally {
            os.close();
        }
        CaptchaCodes captchaCode = new CaptchaCodes();
        captchaCode.setCode(code);
        captchaCode.setSecretCode(secretCode);
        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setImage(os.toString());
        captchaResponse.setSecret("data:image/png;base64, "
                + captchaCode.getSecretCode());
        captchaResponse.setCode(captchaCode.getCode());
        return captchaResponse;
    }
}
