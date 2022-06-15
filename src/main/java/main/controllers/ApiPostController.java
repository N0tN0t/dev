package main.controllers;

import main.dto.PostDTO;
import main.api.response.PostResponse;
import main.dto.UserDTO;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.service.PostService;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiPostController {
    private UserService userService;
    private PostService postService;
//    @Autowired
//    private PostVotesRepository postVotesRepository;
//    @Autowired
//    private PostCommentsRepository postCommentsRepository;
    @GetMapping("/post")
    public ResponseEntity<PostResponse> posts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.findAll(offset, limit, mode));
    }
}
