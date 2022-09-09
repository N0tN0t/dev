package main.service;

import lombok.AllArgsConstructor;
import main.api.response.LoginResponse;
import main.api.response.ResultResponse;
import main.api.response.ResultsResponse;
import main.api.response.UserLoginResponse;
import main.dto.UserDTO;
import main.entities.Posts;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.requests.EmailRequest;
import main.requests.LoginRequest;
import main.requests.ModerationRequest;
import main.respositories.PostRepository;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Email;
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
    public EmailServiceImpl emailService;
    public PostRepository postRepository;

    public ResultResponse getLogoutResponse() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        ResultResponse response = new ResultResponse();
        response.setAuthenticated(auth.isAuthenticated());
        return response;
    }

    public LoginResponse getLogin(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
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

    public ResultsResponse restore(EmailRequest emailRequest) {
        ResultsResponse response = new ResultsResponse();
        response.setResult(false);
        if (userRepository.findByEmail(emailRequest.getEmail()) != null) {
            String hash = UUID.randomUUID().toString().replaceAll("-", "");
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            emailService.sendSimpleMessage(emailRequest.getEmail(), "Restore Password", "Ссылка для смены пароля\n" + baseUrl + "/login/change-password/" + hash);
            Users user = userRepository.findByEmail(emailRequest.getEmail()).get();
            user.setCode(hash);
            userRepository.save(user);
            try {
                captchaService.getCaptcha(); // generate new captcha
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setResult(true);
        }
        return response;
    }

}
