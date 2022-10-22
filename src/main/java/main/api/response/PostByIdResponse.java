package main.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.CommentDTO;
import main.dto.UserDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostByIdResponse {
    private int id;
    private long timestamp;
    private boolean active;
    @JsonIgnoreProperties({"email", "photo", "moderation", "moderationCount", "settings"})
    private UserDTO user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<CommentDTO> comments;
    private List<String> tags;
}
