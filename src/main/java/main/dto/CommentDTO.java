package main.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    int id;
    Date timestamp;
    String text;
    UserDTO user;
}
