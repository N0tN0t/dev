package main.dto;

import lombok.Data;

@Data
public class UserDTO {
    int id;
    String name;
    String photo;
    String email;
    boolean moderation;
    int moderationCount;
    boolean settings;
}
