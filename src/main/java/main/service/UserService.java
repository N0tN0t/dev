package main.service;

import main.api.response.CaptchaResponse;
import main.api.response.RegResponse;
import main.dto.UserDTO;
import main.entities.GlobalSettings;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.requests.RegRequest;
import main.respositories.SettingsRepository;
import main.respositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMappingUtils mappingUtils;
    private CaptchaService captchaService;
    private SettingsRepository settingsRepository;
    private Map<String, Integer> logedIn;
    public static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder(12);

    public UserService(SettingsRepository settingsRepository, UserRepository userRepository, UserMappingUtils mappingUtils, CaptchaService captchaService) {
        this.userRepository = userRepository;
        this.mappingUtils = mappingUtils;
        this.captchaService = captchaService;
        this.settingsRepository = settingsRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }

    public UserDTO findById(Integer id) {
        return mappingUtils.mapToPostDto(userRepository.findById(id).orElse(new Users()));
    }

    public UserDTO findByEmail(String email) {
        return mappingUtils.mapToPostDto(userRepository.findByEmail(email).orElse(new Users()));
    }

    public RegResponse register(RegRequest regRequest) throws IOException {
        GlobalSettings multiuserMode = settingsRepository.findBySettingsCode("MULTIUSER_MODE");
        RegResponse response = new RegResponse();
        ArrayList list = new ArrayList();
        Map<String, String> errors = null;
        Users users = new Users();
        CaptchaResponse captchaResponse = captchaService.getCaptcha();
        if (multiuserMode.getValue() == "NO") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Multiuser mode disabled");
        }
        if (regRequest.getEmail().contains("@") && regRequest.getEmail().contains(".")) {
            if (!regRequest.getName().contains(" ")) {
                if (regRequest.getPassword().length() > 6) {
                    if (captchaService.findCaptcha(regRequest.getCaptchaSecret()) != null) {
                        users.setName(regRequest.getName());
                        users.setEmail(regRequest.getEmail());
                        users.setPassword(BCRYPT.encode(regRequest.getPassword()));
                        users.setCode(captchaResponse.getSecret());
                        users.setPhoto("");
                        Date date = new Date(System.currentTimeMillis());
                        users.setRegTime(date);
                        users.setIsModerator((short) 0);
                    }
                }
            }
        }
        if (!regRequest.getEmail().contains("@") || !regRequest.getEmail().contains(".")) {
            errors.put("e-mail", "Этот e-mail уже зарегистрирован");
        }
        if (regRequest.getName().contains(" ")) {
            errors.put("name", "Имя указано неверно");
        }
        if (regRequest.getPassword().length() <= 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }
        if (captchaService.findCaptcha(regRequest.getCaptchaSecret()) == null) {
            errors.put("captcha", "Код с картинки введён неверно");
        }
        if (errors.isEmpty()) {
            userRepository.save(users);
            response.setResult(true);
        } else {
            response.setResult(false);
            response.setErrors(errors);
        }
        return response;
    }

    public List login(String email, String password) {
        ArrayList result = new ArrayList();
        Users users = null;
        for (Users userr : userRepository.findAll()) {
            if (userr.getEmail() == email) {
                users = userr;
            }
        }
        if (users == null || users.getPassword() != password) {
            result.add(false);
        } else {
            result.add(true);
            result.add(users);
            logedIn.put(users.getEmail(), users.getId());
        }
        return result;
    }

    public boolean logout(String email) {
        logedIn.remove(email);
        return true;
    }
}
