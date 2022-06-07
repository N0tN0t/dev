package main.dto;

import lombok.Data;
import main.entities.Users;

import java.util.Date;
import java.util.Optional;

@Data
public class PostDTO {
    int id;
    Date timestamp;
    Users user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;
}
