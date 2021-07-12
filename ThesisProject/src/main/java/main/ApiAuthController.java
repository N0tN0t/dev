package main;

import main.Entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiAuthController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/api/auth/{id}")
    public Users get(@PathVariable int id) {
        return userRepository.findById(id).get();
    }
    @GetMapping("/api/auth/")
    public List<Users> getList() {
        return (List<Users>)userRepository.findAll();
    }
    @PostMapping("/api/auth/")
    public void post(Users task) {
        userRepository.save(task);
    }
    @PutMapping("/api/auth/{id}")
    public void put(@PathVariable int id, @PathVariable Users task) {
        if (Storage.getPosts().get(id) != null) {
            userRepository.save(task).setId(id);
        }
    }
    @PutMapping("/api/auth/")
    public void putAll(@PathVariable Users task) {
        for (int i = 0;i<Storage.getPosts().size()-1;i++) {
            userRepository.findAll().forEach(task1 -> task1 = task);
        }
    }
    @DeleteMapping("/api/auth/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }
    @DeleteMapping("/api/auth/")
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
