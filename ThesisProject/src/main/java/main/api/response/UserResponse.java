package main.api.response;

import main.entities.Users;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserResponse {
    private Integer id;
    private Integer is_moderator;
    private Date reg_time;
    private String name;
    private String email;
    private String password;
    private String code;
    private String photo;

    public UserResponse(Users user) {
        id = user.getId();
        is_moderator = user.getIs_moderator();
        reg_time = user.getReg_time();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        code = user.getCode();
        photo = user.getPhoto();
    }
}
