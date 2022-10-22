package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import main.dto.UserDTO;

@Getter
@Setter
public class CheckResponse {
    private boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO user;
}

