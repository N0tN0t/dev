package main.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class TagListResponse {
    private List<TagResponse> tagResponseBody;
}