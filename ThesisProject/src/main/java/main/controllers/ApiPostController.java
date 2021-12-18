package main.controllers;

import com.google.common.collect.Iterables;
import main.entities.PostComments;
import main.entities.PostVotes;
import main.entities.Posts;
import main.api.response.PostResponse;
import main.entities.Users;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.respositories.PostRepository;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiPostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostVotesRepository postVotesRepository;
    @Autowired
    private PostCommentsRepository postCommentsRepository;
    @GetMapping("/api/post")
    @ResponseBody
    public PostResponse getPosts(@RequestParam(name = "mode", defaultValue = "recent") String mode) {
        PostResponse postResponse = null;
        Iterable<Posts> AllPosts = postRepository.findAll();
        if (mode.equals("recent")) {
            for (Posts post:AllPosts) {
                postResponse = new PostResponse(post,userRepository,postVotesRepository,postCommentsRepository);
            }
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
