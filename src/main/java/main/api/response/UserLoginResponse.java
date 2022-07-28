package main.api.response;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserLoginResponse {
    private long id;
    private String name;
    private String photo;
    private String email;
    private short moderation;
    private int moderationCount;
    private boolean settings;
}
