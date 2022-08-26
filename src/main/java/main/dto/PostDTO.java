package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import main.entities.Users;

import java.util.Date;

@Data
public class PostDTO {
    int id;
    Date timestamp;
    @JsonIgnoreProperties({"email", "photo", "moderation", "moderationCount", "settings"})
    UserDTO user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;
}
