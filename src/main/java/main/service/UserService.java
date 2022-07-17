package main.service;

import main.dto.UserDTO;
import main.entities.User;
import main.mappings.UserMappingUtils;
import main.requests.RegRequest;
import main.respositories.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;

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
        return mappingUtils.mapToPostDto(userRepository.findById(id).orElse(new User()));
    }
    public UserDTO findByEmail(String email) {
        return mappingUtils.mapToPostDto(userRepository.findByEmail(email).orElse(new User()));
    }

    public ArrayList register(@RequestBody RegRequest regRequest) throws IOException {
        ArrayList list = new ArrayList();
        ArrayList errors = new ArrayList();
        User user = new User();
        if (regRequest.getEmail().contains("@") && regRequest.getEmail().contains(".")) {
            if (!regRequest.getName().contains(" ")) {
                if (regRequest.getPassword().length() > 6) {
                    if (captchaService.getCaptcha().getSecret().equals(regRequest.getCaptchaSecret()) && captchaService.getCaptcha().getCode().equals(regRequest.getCaptcha())) {
                        user.setName(regRequest.getName());
                        user.setEmail(regRequest.getEmail());
                        user.setPassword(regRequest.getPassword());
                    }
                }
            }
        }
        if (!regRequest.getEmail().contains("@") && !regRequest.getEmail().contains(".")) {
            errors.add("Этот e-mail уже зарегистрирован");
        }
        if (regRequest.getName().contains(" ")) {
            errors.add("Имя указано неверно");
        }
        if (regRequest.getPassword().length() <= 6) {
            errors.add("Пароль короче 6-ти символов");
        }
        if (!captchaService.getCaptcha().getSecret().equals(regRequest.getCaptchaSecret()) && !captchaService.getCaptcha().getCode().equals(regRequest.getCaptcha())) {
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
        User user = null;
        for (User userr: userRepository.findAll()) {
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
