package main.api.response;

import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {
    private Integer id;
    private Date timestamp;
    private UserResponse user;
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private int commentCount;
    private int viewCount;
}
