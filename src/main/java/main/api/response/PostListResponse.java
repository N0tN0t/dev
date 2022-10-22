package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostListResponse {
    private Long count;
    private List posts;
}
