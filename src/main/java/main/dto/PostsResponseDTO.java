package main.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostsResponseDTO {
    private long totalElements;
    private List<PostDTO> list;

    public PostsResponseDTO(long totalElements, List<PostDTO> list) {
        this.totalElements = totalElements;
        this.list = list;
    }
}
