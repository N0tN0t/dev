package main.service;

import lombok.AllArgsConstructor;
import main.api.response.CheckResponse;
import main.api.response.LoginResponse;
import main.api.response.ResultResponse;
import main.api.response.UserLoginResponse;
import main.dto.UserDTO;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.requests.LoginRequest;
import main.respositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@AllArgsConstructor
@Service
public class AuthCheckService {
    public AuthenticationManager authenticationManager;
    public UserService userService;
    public UserRepository userRepository;
    public UserMappingUtils mappingService;
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
        Users users = (Users) auth.getPrincipal();
        return getLoginResponse(users.getEmail());
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
}
