package main.dto;

import lombok.Data;

@Data
public class UserDTO {
    Integer id;
    String name;
    String photo;
    String email;
    Boolean moderation;
    Integer moderationCount;
    Boolean settings;
}
