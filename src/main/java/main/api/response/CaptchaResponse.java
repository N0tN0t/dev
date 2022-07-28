package main.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class CaptchaResponse {
    private String code;
    private String secret;
    private String image;
}
