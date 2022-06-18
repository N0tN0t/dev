package main.dto;

import lombok.Data;
import main.api.response.PostInfoResponse;
import main.entities.Posts;

import java.util.List;

@Data
public class PostsResponseDTO {
    private long totalElements;
    private List<PostInfoResponse> list;

    public PostsResponseDTO(long totalElements, List<PostInfoResponse> list) {
        this.totalElements = totalElements;
        this.list = list;
    }
}
