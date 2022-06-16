package main.dto;

import lombok.Data;
import main.api.response.PostInfoResponse;
import main.entities.Posts;

import java.util.List;

@Data
public class PostsResponseDTO {
    private List<Posts> posts;
    private List<PostInfoResponse> postInfoResponses;
}
