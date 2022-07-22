package main.service;

import main.api.response.ResultResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthCheckService {
    public ResultResponse getLogoutResponse() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        ResultResponse response = new ResultResponse();
        response.setAuthenticated(auth.isAuthenticated());
        return response;
    }
}
