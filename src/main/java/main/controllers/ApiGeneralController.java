package main.controllers;

import main.api.response.*;
import main.requests.CommentRequest;
import main.requests.ProfileRequest;
import main.requests.SettingsRequest;
import main.service.GeneralService;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;
    private final PostService postService;
    private final GeneralService generalService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagService tagService, PostService postService, GeneralService generalService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
        this.generalService = generalService;
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
    @PutMapping("/settings")
    public ResponseEntity<SettingsResponse> putSettings(@RequestBody SettingsRequest settingsRequest) {
        return ResponseEntity.ok(settingsService.putGlobalSettings(settingsRequest));
    }

    @GetMapping("/tag")
    public ResponseEntity<TagListResponse> tags(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(tagService.getTags(query));
    }

    @PostMapping("/image")
    public ResponseEntity<ArrayList> image(File image) {
        return ResponseEntity.ok(generalService.postImage(image));
    }

    @PostMapping("/comment")
    public ResponseEntity<ArrayList> comment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(generalService.postComment(commentRequest));
    }

    @PostMapping("/profile/my")
    public ResponseEntity<ArrayList> editMyProfile(@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(generalService.editMyProfile(profileRequest));
    }

    @GetMapping("/statistics/my")
    public ResponseEntity<StatisticsResponse> myStatistics() {
        return ResponseEntity.ok(generalService.myStatistics());
    }
    @GetMapping("/statistics/all")
    public ResponseEntity<StatisticsResponse> allStatistics() {
        return ResponseEntity.ok(generalService.allStatistics());
    }
}
