package main.controllers;

import main.api.response.PostByIdResponse;
import main.api.response.PostListResponse;
import main.api.response.ResultsResponse;
import main.requests.PostRequest;
import main.requests.PostVoteRequest;
import main.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class ApiPostController {
    private PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
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
            @RequestParam(required = false) String query) {
        return ResponseEntity.ok(postService.findPostsByQuery(offset, limit, query));
    }

    @GetMapping("/post/byDate")
    public ResponseEntity<PostListResponse> postsByDate(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam String date) throws ParseException {
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

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/post")
    public ResponseEntity<ResultsResponse> postPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.postPost(postRequest));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PutMapping("/post/{ID}")
    public ResponseEntity<ResultsResponse> editPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.editPost(postRequest));
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/post/moderation")
    public ResponseEntity<PostListResponse> postsWithStatus(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "new") String status) {
        return ResponseEntity.ok(postService.getPostsWithStatus(offset, limit, status));
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/post/my")
    public ResponseEntity<PostListResponse> myPosts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "published") String status) {
        return ResponseEntity.ok(postService.getMyPosts(offset, limit, status));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/post/like")
    public ResponseEntity<ResultsResponse> like(@RequestBody PostVoteRequest postVoteRequest) {
        return ResponseEntity.ok(postService.likePost(postVoteRequest));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/post/dislike")
    public ResponseEntity<ResultsResponse> dislike(@RequestBody PostVoteRequest postVoteRequest) {
        return ResponseEntity.ok(postService.dislikePost(postVoteRequest));
    }
}
