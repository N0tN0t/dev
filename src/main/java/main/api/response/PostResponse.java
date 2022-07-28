package main.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Getter
@Setter
@Service
@AllArgsConstructor
public class PostResponse {
    private Integer id;
    private Date timestamp;
    @JsonIgnoreProperties({"photo","email","moderation","moderationCount","settings"})
    private UserDTO user;
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private int commentCount;
    private int viewCount;
}
