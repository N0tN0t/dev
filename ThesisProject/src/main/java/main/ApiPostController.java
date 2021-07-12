package main;

import main.Entities.Posts;
import main.api.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class ApiPostController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostVotesRepository postVotesRepository;
    @Autowired
    private PostCommentsRepository postCommentsRepository;
    @GetMapping("/api/post")
    public PostResponse getPosts() {
        PostResponse postResponse = new PostResponse();
        if (taskRepository.count() != 0) {
            postResponse.setCount((int) taskRepository.count());
            for (int i = 0; i < taskRepository.count(); i++) {
                Posts post = taskRepository.findById(i).get();
                int likes = 0;
                int dislikes = 0;
                for (int b = 0; b < postVotesRepository.count(); b++) {
                    if (postVotesRepository.findById(b).get().getValue() == 1) {
                        likes++;
                    } else if (postVotesRepository.findById(b).get().getValue() == -1) {
                        dislikes++;
                    }
                }
                postResponse.addPosts(taskRepository.findById(i).get(), 1592338706, userRepository.findById(post.getUser_id()).get(), likes, dislikes, (int) postCommentsRepository.count());
            }
        }
        else {
            postResponse.setCount(0);
        }
        return postResponse;
    }
}
