package main.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaResponse {
    private String code;
    private String secret;
    private String image;
}
