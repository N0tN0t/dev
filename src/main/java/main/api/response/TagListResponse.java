package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.entities.Tags;

import java.util.List;

@Getter
@Setter
public class TagListResponse {
    private List<Tags> tags;
}