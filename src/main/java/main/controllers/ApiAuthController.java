package main.controllers;

import main.api.response.*;
import main.entities.Users;
import main.requests.LoginRequest;
import main.dto.UserDTO;
import main.requests.RegRequest;
import main.service.AuthCheckService;
import main.service.CaptchaService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckResponse checkResponse;
    private UserService userService;
    private CaptchaService captchaService;
    private final AuthenticationManager authenticationManager;
    private AuthCheckService authCheckService;

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
        Users users = (Users) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(users.getEmail()));
    }
    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/logout")
    public ResponseEntity<ResultResponse> logout() {
        return ResponseEntity.ok(authCheckService.getLogoutResponse());
    }

    private LoginResponse getLoginResponse(String email) {
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
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }
}
