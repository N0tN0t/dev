package main.mappings;

import main.dto.CommentDTO;
import main.dto.PostDTO;
import main.entities.PostComments;
import main.entities.Posts;
import main.entities.Users;
import main.respositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentMappingUtils {
    UserRepository userRepository;
    UserMappingUtils userMappingUtils;

    public CommentMappingUtils(UserRepository userRepository, UserMappingUtils userMappingUtils) {
        this.userMappingUtils = userMappingUtils;
        this.userRepository = userRepository;
    }

    public CommentDTO mapToCommentDto(PostComments entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setTimestamp(entity.getTime().getTime() / 1000);
        dto.setText(entity.getText());
        dto.setUser(userMappingUtils.mapToPostDto(userRepository.findById(entity.getUsers().getId()).orElse(new Users())));
        return dto;
    }

    public PostComments mapToCommentEntity(CommentDTO dto) {
        PostComments entity = new PostComments();
        entity.setUsers(userMappingUtils.mapToPostEntity(dto.getUser()));
        entity.setId(dto.getId());
        entity.setTime(new Date(dto.getTimestamp()));
        entity.setText(dto.getText());
        return entity;
    }
}
