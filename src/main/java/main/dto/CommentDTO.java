package main.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    int id;
    long timestamp;
    String text;
    UserDTO user;
}
