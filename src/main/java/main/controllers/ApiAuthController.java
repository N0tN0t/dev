package main.controllers;

import main.api.response.CheckResponse;
import main.dto.UserDTO;
import main.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckResponse checkResponse;
    private UserService userService;

    public ApiAuthController(CheckResponse checkResponse) {
        this.checkResponse = checkResponse;
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
}
