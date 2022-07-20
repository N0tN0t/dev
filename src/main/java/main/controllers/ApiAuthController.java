package main.controllers;

import com.github.cage.GCage;
import main.api.response.CaptchaResponse;
import main.api.response.CheckResponse;
import main.entities.CaptchaCodes;
import main.requests.LoginRequest;
import main.api.response.LoginResponse;
import main.api.response.UserLoginResponse;
import main.dto.UserDTO;
import main.entities.User;
import main.requests.RegRequest;
import main.service.CaptchaService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckResponse checkResponse;
    private UserService userService;
    private CaptchaService captchaService;
    private final AuthenticationManager authenticationManager;

    public ApiAuthController(CheckResponse checkResponse, UserService userService, AuthenticationManager authenticationManager) {
        this.checkResponse = checkResponse;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
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
    private ResponseEntity<LoginResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @PostMapping("/register")
    public ArrayList register(RegRequest regRequest) throws IOException {
        return userService.register(regRequest);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(user.getEmail()));
    }
    @GetMapping("/logout")
    public boolean logout(String email) {
        return userService.logout(email);
    }

    private LoginResponse getLoginResponse(String email) {
        UserDTO currentUser = userService.findByEmail(email);
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.isModeration());
        userResponse.setId(currentUser.getId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return loginResponse;
    }
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }
}
