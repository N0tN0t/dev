package main.controllers;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.CheckResponse;
import main.dto.UserDTO;
import main.entities.CaptchaCodes;
import main.entities.Users;
import main.respositories.UserRepository;
import main.service.CaptchaService;
import main.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckResponse checkResponse;
    private UserService userService;
    private CaptchaService captchaService;
    private UserRepository userRepository;

    public ApiAuthController(CheckResponse checkResponse) {
        this.checkResponse = checkResponse;
    }

    @GetMapping()
    public List<UserDTO> getList() {
        return userService.findAll();
    }
    @PostMapping()
    public void post(UserDTO task) {
        userService.findAll().set(userService.findAll().size(),task);
    }
    @PutMapping()
    public void putAll(@PathVariable UserDTO task) {
        for (int i = 0;i<userService.findAll().size()-1;i++) {
            userService.findAll().forEach(task1 -> task1 = task);
        }
    }
    @DeleteMapping()
    public void deleteAll() {
        userService.findAll().clear();
    }
    @GetMapping("/check")
    private CheckResponse check() {
        return checkResponse;
    }

    public List register(String email, String password, String name, String captcha, String captcha_secret) throws IOException {
        ArrayList list = new ArrayList();
        ArrayList errors = new ArrayList();
        Users user = new Users();
        if (email.contains("@") && email.contains(".")) {
            if (!name.contains(" ")) {
                if (password.length() > 6) {
                    if (captchaService.getCaptcha().getSecret().equals(captcha_secret) && captchaService.getCaptcha().getCode().equals(captcha)) {
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                    }
                }
            }
        }
        if (!email.contains("@") && !email.contains(".")) {
            errors.add("Этот e-mail уже зарегистрирован");
        }
        if (name.contains(" ")) {
            errors.add("Имя указано неверно");
        }
        if (password.length() <= 6) {
            errors.add("Пароль короче 6-ти символов");
        }
        if (!captchaService.getCaptcha().getSecret().equals(captcha_secret) && !captchaService.getCaptcha().getCode().equals(captcha)) {
            errors.add("Код с картинки введён неверно");
        }
        if (!errors.isEmpty()){
            userRepository.save(user);
            list.add(true);
        }
        else {
            list.add(false);
            list.add(errors);
        }
        return list;
    }
}
