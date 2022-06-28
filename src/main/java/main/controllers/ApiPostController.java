package main.controllers;

import main.api.response.PostListResponse;
import main.service.PostService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiPostController {
    private UserService userService;
    private PostService postService;
//    @Autowired
//    private PostVotesRepository postVotesRepository;
//    @Autowired
//    private PostCommentsRepository postCommentsRepository;
    @GetMapping("/post")
    //public ResponseEntity<List<PostResponse>> posts(
    public ResponseEntity<PostListResponse> posts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }
}
