package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import main.dto.UserDTO;
import main.entities.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
@Component
public class CheckResponse {
    private boolean result = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO user;
}

