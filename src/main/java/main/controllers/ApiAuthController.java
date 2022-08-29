package main.controllers;

import main.api.response.*;
import main.entities.Users;
import main.requests.LoginRequest;
import main.dto.UserDTO;
import main.requests.PasswordRequest;
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
    private UserService userService;
    private CaptchaService captchaService;
    private AuthCheckService authCheckService;

    public ApiAuthController(CaptchaService captchaService,AuthCheckService authCheckService, UserService userService) {
        this.captchaService = captchaService;
        this.authCheckService = authCheckService;
        this.userService = userService;
    }
    @GetMapping("/check")
    private ResponseEntity<LoginResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(authCheckService.getLoginResponse(principal.getName()));
    }

    @PostMapping("/register")
    public ArrayList register(@RequestBody RegRequest regRequest) throws IOException {
        return userService.register(regRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authCheckService.getLogin(loginRequest));
    }
    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/logout")
    public ResponseEntity<ResultResponse> logout() {
        return ResponseEntity.ok(authCheckService.getLogoutResponse());
    }
    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }
    @PostMapping("/password")
    public ResponseEntity<ArrayList> changePassword(@RequestBody PasswordRequest passwordRequest) throws IOException {
        return ResponseEntity.ok(captchaService.changePassword(passwordRequest));
    }
}
