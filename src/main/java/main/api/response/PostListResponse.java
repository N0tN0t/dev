package main.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class PostListResponse {
    private Long count;
    private List posts;
}
