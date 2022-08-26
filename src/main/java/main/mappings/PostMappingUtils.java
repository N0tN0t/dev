package main.mappings;

import main.dto.PostDTO;
import main.entities.Posts;
import main.entities.Users;
import main.respositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostMappingUtils {
    UserRepository userRepository;
    UserMappingUtils userMappingUtils;

    public PostMappingUtils(UserRepository userRepository,UserMappingUtils userMappingUtils) {
        this.userRepository = userRepository;
        this.userMappingUtils = userMappingUtils;
    }

    public PostDTO mapToPostDto(Posts entity){
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setTimestamp(entity.getTime());
        dto.setUser(userMappingUtils.mapToPostDto(userRepository.findById(entity.getUsers().getId()).orElse(new Users())));
        dto.setAnnounce(entity.getTitle());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }
    public Posts mapToPostEntity(PostDTO dto){
        Posts entity = new Posts();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getAnnounce());
        entity.setUsers(userMappingUtils.mapToPostEntity(dto.getUser()));
        entity.setTime(dto.getTimestamp());
        entity.setViewCount(dto.getViewCount());
        return entity;
    }
}
