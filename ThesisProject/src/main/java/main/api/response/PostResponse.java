package main.api.response;

import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import lombok.Data;
import main.entities.Posts;
import main.entities.Users;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {
    private Integer id;
    private Date timestamp;
    private UserResponse user;      //такой же класс с информацией о пользователе
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private int commentCount;
    private int viewCount;
    private int userId;
    private String userName;
    private List<List> posts;

    public PostResponse(Posts post,UserRepository userRepository, PostVotesRepository postVotesRepository, PostCommentsRepository postCommentsRepository) {
        Iterable<Users> AllUsers = userRepository.findAll();
        Users users = null;
        for (Users currentUser:AllUsers) {
            if (currentUser.getId() == post.getUser_id()){
                users = currentUser;
            }
        }
        id = post.getId();
        timestamp = post.getTime();
        user = new UserResponse(users);
        title = post.getTitle();
        announce = post.getText();
        int likes = 0;
        int dislikes = 0;
        for (int b = 0; b < Iterables.size(postVotesRepository.findAll()); b++) {
            if (Iterables.get(postVotesRepository.findAll(), b).getValue() == 1) {
                likes++;
            } else if (Iterables.get(postVotesRepository.findAll(), b).getValue() == -1) {
                dislikes++;
            }
        }
        likeCount = likes;
        dislikeCount = dislikes;
        commentCount = Iterables.size(postCommentsRepository.findAll());
        viewCount = post.getView_count();
        userId = post.getUser_id();
        userName = users.getName();

        posts.add(Arrays.asList(id,timestamp,user,title,announce,likeCount,dislikeCount,commentCount,viewCount,userId,userName));
    }
}
