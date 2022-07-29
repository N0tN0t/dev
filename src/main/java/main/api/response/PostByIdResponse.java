package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.entities.PostComments;
import main.entities.Tags;
import main.entities.Users;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostByIdResponse {
    Integer id;
    Date timestamp;
    Integer active;
    Users users;
    String title;
    String text;
    Integer likeCount;
    Integer dislikeCount;
    Integer viewCount;
    PostComments comments;
    List<Tags> tags;
}
