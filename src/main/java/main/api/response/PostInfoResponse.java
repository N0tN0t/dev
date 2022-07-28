package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Getter
@Setter
@Service
@AllArgsConstructor
public class PostInfoResponse {
    private ArrayList comments;
    private ArrayList tags;
}
