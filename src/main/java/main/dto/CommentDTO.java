package main.dto;

import lombok.Data;

@Data
public class CommentDTO {
    int id;
    long timestamp;
    String text;
    UserDTO user;
}
