package main.service;

import lombok.AllArgsConstructor;
import main.api.response.LoginResponse;
import main.api.response.ResultResponse;
import main.api.response.UserLoginResponse;
import main.dto.UserDTO;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.requests.EmailRequest;
import main.requests.LoginRequest;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthCheckService {
    public AuthenticationManager authenticationManager;
    public UserService userService;
    public UserRepository userRepository;
    public UserMappingUtils mappingService;
    public CaptchaService captchaService;
    @Autowired
    private JavaMailSender mailSender;
    public ResultResponse getLogoutResponse() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        ResultResponse response = new ResultResponse();
        response.setAuthenticated(auth.isAuthenticated());
        return response;
    }
    public LoginResponse getLogin(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return getLoginResponse(auth.getName());
    }

    public LoginResponse getLoginResponse(String email) {
        UserDTO currentUser = userService.findByEmail(email);
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getModeration());
        userResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return loginResponse;
    }

    public Boolean restore(EmailRequest emailRequest) {
        boolean result = false;
        if (userRepository.findByEmail(emailRequest.getEmail()) != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            String hash = UUID.randomUUID().toString().replaceAll("-","");
            message.setFrom("sansundertalesans@mail.ru");
            message.setTo(emailRequest.getEmail());
            message.setSubject("Restore Password");
            message.setText(emailRequest.getEmail()+"/change-password/"+hash);
            Users user = userRepository.findByEmail(emailRequest.getEmail()).get();
            user.setCode(hash);
            userRepository.save(user);
            try {
                captchaService.getCaptcha(); // generate new captcha
            } catch (IOException e) {
                e.printStackTrace();
            }
            mailSender.send(message);
            result = true;
        }
        return result;
    }
}
