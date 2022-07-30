package main.controllers;

import main.api.response.PostByIdResponse;
import main.api.response.PostListResponse;
import main.service.PostService;
import main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApiPostController {
    private UserService userService;
    private PostService postService;
    public ApiPostController(UserService userService,PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }
    @GetMapping("/post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostListResponse> posts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }
    @GetMapping("/post/search")
    public ResponseEntity<PostListResponse> postsWithQuery(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String query) {
        return ResponseEntity.ok(postService.findPostsByQuery(offset, limit, query));
    }

    @GetMapping("/post/byDate")
    public ResponseEntity<PostListResponse> postsByDate(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam Date date) {
        return ResponseEntity.ok(postService.findPostsByDate(offset, limit, date));
    }

    @GetMapping("/post/byTag")
    public ResponseEntity<PostListResponse> postsByTag(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String tag) {
        return ResponseEntity.ok(postService.findPostsByTag(offset, limit, tag));
    }
    @GetMapping("post/{id}")
    public ResponseEntity<PostByIdResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(postService.findPostsById(id));
    }

    @GetMapping("/post/moderation")
    public ResponseEntity<PostListResponse> postsWithStatus(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "new") String status) {
        return ResponseEntity.ok(postService.getPostsWithStatus(offset, limit, status));
    }

    @GetMapping("/post/my")
    public ResponseEntity<PostListResponse> myPosts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "published") String status) {
        return ResponseEntity.ok(postService.getMyPosts(offset, limit, status));
    }
}
