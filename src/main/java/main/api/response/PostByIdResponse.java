package main.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.CommentDTO;
import main.dto.UserDTO;
import main.entities.PostComments;
import main.entities.Tags;
import main.entities.Users;
import org.springframework.stereotype.Service;

import java.util.Date;
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
