package main.controllers;

import main.api.response.PostListResponse;
import main.service.PostService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiPostController {
    private UserService userService;
    private PostService postService;
    @GetMapping("/post")
    public ResponseEntity<PostListResponse> posts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }
    @GetMapping("/post/moderation")
    public ResponseEntity<PostListResponse> postsWithStatus(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "new") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @GetMapping("/post/moderation")
    public ResponseEntity<PostListResponse> myPosts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "published") String status) {
        return ResponseEntity.ok(postService.getMyPosts(offset, limit, status));
    }
}
