package main.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    int parent_id;
    int post_id;
    String text;
}
