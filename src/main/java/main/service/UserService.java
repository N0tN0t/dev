package main.service;

import main.api.response.CaptchaResponse;
import main.dto.UserDTO;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.requests.RegRequest;
import main.respositories.UserRepository;
import org.springframework.stereotype.Service;

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
    private Map<String,Integer> logedIn;

    public UserService(UserRepository userRepository,UserMappingUtils mappingUtils,CaptchaService captchaService) {
        this.userRepository = userRepository;
        this.mappingUtils = mappingUtils;
        this.captchaService =captchaService;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }
    public UserDTO findById(Integer id){
        return mappingUtils.mapToPostDto(userRepository.findById(id).orElse(new Users()));
    }
    public UserDTO findByEmail(String email) {
        return mappingUtils.mapToPostDto(userRepository.findByEmail(email).orElse(new Users()));
    }

    public ArrayList register(RegRequest regRequest) throws IOException {
        ArrayList list = new ArrayList();
        ArrayList errors = new ArrayList();
        Users users = new Users();
        CaptchaResponse captchaResponse = captchaService.getCaptcha();
        if (regRequest.getEmail().contains("@") && regRequest.getEmail().contains(".")) {
            if (!regRequest.getName().contains(" ")) {
                if (regRequest.getPassword().length() > 6) {
                    System.out.println(captchaService.findCaptcha(regRequest.getCaptchaSecret()).getSecretCode());
                    System.out.println(captchaResponse.getSecret());
                    System.out.println(regRequest.getCaptchaSecret());
                    if (captchaService.findCaptcha(regRequest.getCaptchaSecret()) != null) {
                        users.setName(regRequest.getName());
                        users.setEmail(regRequest.getEmail());
                        users.setPassword(regRequest.getPassword());
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
            errors.add("Этот e-mail уже зарегистрирован");
        }
        if (regRequest.getName().contains(" ")) {
            errors.add("Имя указано неверно");
        }
        if (regRequest.getPassword().length() <= 6) {
            errors.add("Пароль короче 6-ти символов");
        }
        if (captchaService.findCaptcha(regRequest.getCaptchaSecret()) == null) {
            errors.add("Код с картинки введён неверно");
        }
        if (!errors.isEmpty()){
            userRepository.save(users);
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
        Users users = null;
        for (Users userr: userRepository.findAll()) {
            if (userr.getEmail() == email) {
                users = userr;
            }
        }
        if (users == null || users.getPassword() != password) {
            result.add(false);
        }else {
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
