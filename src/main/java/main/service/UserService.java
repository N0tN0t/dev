package main.service;

import main.dto.UserDTO;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.respositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private UserRepository userRepository;
    private UserMappingUtils mappingUtils;
    private CaptchaService captchaService;
    private Map<String,Integer> logedIn;
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }
    public UserDTO findById(Integer id){
        return mappingUtils.mapToPostDto(userRepository.findById(id).orElse(new Users()));
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

    public List login(String email, String password) {
        ArrayList result = new ArrayList();
        Users user = null;
        for (Users userr: userRepository.findAll()) {
            if (userr.getEmail() == email) {
                user = userr;
            }
        }
        if (user == null || user.getPassword() != password) {
            result.add(false);
        }else {
            result.add(true);
            result.add(user);
            logedIn.put(user.getEmail(), user.getId());
        }
        return result;
    }
    public boolean logout(String email) {
        logedIn.remove(email);
        return true;
    }
}
