package main.dto;

import lombok.Data;
import main.entities.User;

import java.util.Date;

@Data
public class PostDTO {
    int id;
    Date timestamp;
    User user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;
}
