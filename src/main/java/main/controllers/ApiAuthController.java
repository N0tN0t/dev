package main.controllers;

import main.api.response.CheckResponse;
import main.api.response.InitResponse;
import main.entities.Users;
import main.respositories.UserRepository;
import main.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckResponse checkResponse;
    @Autowired
    private UserRepository userRepository;

    public ApiAuthController(CheckResponse checkResponse) {
        this.checkResponse = checkResponse;
    }

    @GetMapping()
    public List<Users> getList() {
        return (List<Users>)userRepository.findAll();
    }
    @PostMapping()
    public void post(Users task) {
        userRepository.save(task);
    }
    @PutMapping()
    public void putAll(@PathVariable Users task) {
        for (int i = 0;i<userRepository.count()-1;i++) {
            userRepository.findAll().forEach(task1 -> task1 = task);
        }
    }
    @DeleteMapping()
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @GetMapping("/check")
    private CheckResponse check() {
        return checkResponse;
    }
}
