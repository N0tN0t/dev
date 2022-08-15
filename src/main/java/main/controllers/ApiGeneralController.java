package main.controllers;

import main.api.response.*;
import main.requests.PostRequest;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;
    private final PostService postService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagService tagService, PostService postService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponse> calendar(
            @RequestParam(defaultValue = "false") boolean required) {
        return ResponseEntity.ok(postService.calendar());
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    @GetMapping("/tag")
    public ResponseEntity<TagListResponse> tags(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(tagService.getTags(query));
    }

    @PostMapping("/post")
    public ResponseEntity<ArrayList> postPost(@RequestParam PostRequest postRequest) {
        return ResponseEntity.ok(postService.postPost(postRequest));
    }
    @PostMapping("/post/{ID}")
    public ResponseEntity<ArrayList> editPost(@RequestParam PostRequest postRequest) {
        return ResponseEntity.ok(postService.editPost(postRequest));
    }
}
