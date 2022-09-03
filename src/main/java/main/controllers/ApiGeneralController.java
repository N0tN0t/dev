package main.controllers;

import main.api.response.*;
import main.requests.CommentRequest;
import main.requests.ProfileRequest;
import main.requests.SettingsRequest;
import main.service.GeneralService;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;


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
    public ResponseEntity<CalendarResponse> calendar() {
        return ResponseEntity.ok(postService.calendar());
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> settings() {
        return ResponseEntity.ok().body(null);
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
    public ResponseEntity<?> image(MultipartFile image) {
        return ResponseEntity.ok(generalService.postImage(image));
    }

    @PostMapping("/comment")
    public ResponseEntity<?> comment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(generalService.postComment(commentRequest));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping(value = "/profile/my", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editProfile(
            @RequestParam(value = "photo") MultipartFile photo,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password", required = false) String password) throws IOException {
        return ResponseEntity
                .ok(generalService.editMyProfilePhoto(photo, name, email, password));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/profile/my")
    public ResponseEntity<ArrayList> editProfile(@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(generalService.editMyProfile(profileRequest));
    }

    @GetMapping("/statistics/my")
    public ResponseEntity<StatisticsResponse> myStatistics() {
        return ResponseEntity.ok(generalService.myStatistics());
    }
    @GetMapping("/statistics/all")
    public ResponseEntity<Object> allStatistics() {
        return ResponseEntity.ok(generalService.allStatistics());
    }
}
