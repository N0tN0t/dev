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
    @Autowired
    private PostVotesRepository postVotesRepository;
    @Autowired
    private PostCommentsRepository postCommentsRepository;
    @GetMapping("/api/post")
    @ResponseBody
    public PostResponse getPosts(@RequestParam(name = "mode", defaultValue = "recent") String mode) {
        PostResponse postResponse = null;
        List<PostDTO> AllPosts = postService.findAll();
        List<UserDTO> AllUsers = userService.findAll();
        if (mode.equals("recent")) {
            System.out.println("recent");
        }
        else if (mode.equals("popular")) {
            System.out.println("popular");
        }
        else if (mode.equals("best")) {
            System.out.println("best");
        }
        else if (mode.equals("early")) {
            System.out.println("early");
        }
        ResponseEntity.ok();
        return postResponse;
    }
}
