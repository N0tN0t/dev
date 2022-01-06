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

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private long likeCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private long dislikeCount;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private int commentCount;
    private int viewCount;
    private int userId;
    private String userName;
    private List<List> posts;

    public PostResponse(Posts post) {
        id = post.getId();
        timestamp = post.getTime();
        user = new UserResponse("?");
        title = post.getTitle();
        announce = post.getText();
        viewCount = post.getView_count();
        userId = post.getUser_id();
        userName = null;

        posts.add(Arrays.asList(id,timestamp,user,title,announce,likeCount,dislikeCount,commentCount,viewCount,userId,userName));
    }
}
