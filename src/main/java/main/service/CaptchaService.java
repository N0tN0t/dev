package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.CaptchaResponse;
import main.api.response.ResultResponse;
import main.api.response.ResultsResponse;
import main.entities.CaptchaCodes;
import main.entities.Users;
import main.requests.PasswordRequest;
import main.respositories.CaptchaRepository;
import main.respositories.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CaptchaService {
    private CaptchaRepository captchaRepository;
    private UserRepository userRepository;
    public static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder(12);

    public CaptchaService(UserRepository userRepository, CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
        this.userRepository = userRepository;
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
        for (CaptchaCodes captchaCodes : captchas) {
            if (captchaCodes.getSecretCode().equals(secret)) {
                ifFinded = captchaCodes;
            }
        }
        return ifFinded;
    }

    public ResultsResponse changePassword(PasswordRequest passwordRequest) {
        ResultsResponse response = new ResultsResponse();
        HashMap<String, String> errors = new HashMap<>();
        if (findCaptcha(passwordRequest.getCaptchaSecret()).getCode() != null) {
            boolean oldCaptcha = false;
            for (CaptchaCodes captchaCodes : captchaRepository.findOldCaptcha()) {
                if (passwordRequest.getCaptcha() == captchaCodes.getCode()) {
                    oldCaptcha = true;
                }
            }
            if (oldCaptcha == false) {
                if (passwordRequest.getPassword().length() > 6) {
                    Users user = userRepository.findByCode(passwordRequest.getCode());
                    user.setPassword(BCRYPT.encode(passwordRequest.getPassword()));
                    userRepository.save(user);
                    response.setResult(true);
                } else {
                    errors.put("password", "Пароль короче 6-ти символов");
                }
            } else {
                errors.put("code", "Ссылка для восстановления пароля устарела.\n" +
                        "<a href=\n" +
                        "\"/auth/restore\">Запросить ссылку снова</a>");
            }
        } else {
            errors.put("captcha", "Код с картинки введён неверно");
        }
        if (!errors.isEmpty()) {
            response.setResult(false);
            response.setErrors(errors);
        }
        return response;
    }
}
