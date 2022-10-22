package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PostDTO {
    int id;
    long timestamp;
    @JsonIgnoreProperties({"email", "photo", "moderation", "moderationCount", "settings"})
    UserDTO user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;
}
