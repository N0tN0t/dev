package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.CaptchaResponse;
import main.entities.CaptchaCodes;
import main.respositories.CaptchaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CaptchaService {
    private CaptchaRepository captchaRepository;
    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }
    @Scheduled(fixedRate = 10000)
    public CaptchaResponse getCaptcha() throws IOException {
        Cage cage = new GCage();
        String code = cage.getTokenGenerator().next();
        String secretCode = UUID.randomUUID().toString();
        String image = "data:image/png;base64, " + Base64.getEncoder().encodeToString(cage.draw(code));
        CaptchaCodes captchaCode = new CaptchaCodes();
        captchaCode.setCode(code);
        captchaCode.setSecretCode(secretCode);
        captchaCode.setTime(new Date());
        captchaRepository.save(captchaCode);
        CaptchaResponse captchaResponse = new CaptchaResponse();
        captchaResponse.setImage(image);
        captchaResponse.setSecret(secretCode);
        return captchaResponse;
    }
    @Scheduled(fixedRate = 3600000)
    public void deleteCaptchaCode() {
        captchaRepository.deleteAll(captchaRepository.findOldCaptcha());
    }
    public CaptchaCodes findCaptcha(String secret) {
        CaptchaCodes ifFinded = null;
        List<CaptchaCodes> captchas = captchaRepository.findAllCaptcha();
        for (CaptchaCodes captchaCodes: captchas) {
            if (captchaCodes.getSecretCode().equals(secret)){
                ifFinded = captchaCodes;
            }
        }
        return ifFinded;
    }
}
