package main.controllers;

import main.entities.Posts;
import main.api.response.PostResponse;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.respositories.PostRepository;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
        PostResponse postResponse = new PostResponse();
        if (mode.equals("recent")) {
            System.out.println("recent");
            if (postRepository.count() != 0) {
                postResponse.setCount((int) postRepository.count());
                for (int i = 0; i < postRepository.count(); i++) {
                    Iterable<Posts> post = postRepository.findAllById(Collections.singleton(i));
                    int likes = 0;
                    int dislikes = 0;
                    for (int b = 0; b < postVotesRepository.count(); b++) {
                        if (postVotesRepository.findAllById(Collections.singleton(b)).iterator().next().getValue() == 1) {
                            likes++;
                        } else if (postVotesRepository.findById(b).get().getValue() == -1) {
                            dislikes++;
                        }
                    }
                    postResponse.addPosts(postRepository.findById(i).get(), 1592338706, userRepository.findById(post.iterator().next().getUser_id()).get(), likes, dislikes, (int) postCommentsRepository.count());
                }
            } else {
                postResponse.setCount(0);
            }
            //System.out.println("Complete");
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
