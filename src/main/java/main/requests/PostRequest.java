package main.requests;

import lombok.Getter;
import lombok.Setter;
import main.entities.Tags;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostRequest {
    Date timestamp;
    int active;
    String title;
    List<Tags> tags;
    String text;
}
