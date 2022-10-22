package main.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    long timestamp;
    boolean active;
    String title;
    List<String> tags;
    String text;
}
