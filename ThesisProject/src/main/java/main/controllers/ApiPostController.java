package main.controllers;

import main.entities.Posts;
import main.api.response.PostResponse;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.respositories.TaskRepository;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @ResponseBody
    public PostResponse getPosts(@RequestParam(name = "mode", defaultValue = "recent") String mode) {
        PostResponse postResponse = new PostResponse();
        if (mode.equals("recent")) {
            System.out.println("recent");
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
            } else {
                postResponse.setCount(0);
            }
            //System.out.println("Complete");
        }
        else if (mode.equals("popular")) {
            System.out.println("popular");
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
            } else {
                postResponse.setCount(0);
            }
        }
        else if (mode.equals("best")) {
            System.out.println("best");
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
            } else {
                postResponse.setCount(0);
            }
        }
        else if (mode.equals("early")) {
            System.out.println("early");
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
            } else {
                postResponse.setCount(0);
            }
        }
        ResponseEntity.ok();
        return postResponse;
    }
}
