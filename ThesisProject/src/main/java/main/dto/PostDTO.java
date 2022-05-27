package main.dto;

import lombok.Data;
import main.entities.Users;

import java.util.Date;
import java.util.Optional;

@Data
public class PostDTO {
    Integer id;
    Date timestamp;
    Users user;
    String title;
    String announce;
    Integer likeCount;
    Integer dislikeCount;
    Integer commentCount;
    Integer viewCount;
}
