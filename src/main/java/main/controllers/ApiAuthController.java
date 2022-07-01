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
        return userService.register(email,password,name,captcha,captcha_secret);
    }
}
