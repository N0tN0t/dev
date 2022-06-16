package main.mappings;

import main.dto.PostDTO;
import main.entities.Posts;
import main.entities.Users;
import main.respositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostMappingUtils {
    UserRepository userRepository;
    public PostDTO mapToPostDto(Posts entity){
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setTimestamp(entity.getTime());
        dto.setUser(userRepository.findById(entity.getUser().getId()).orElse(new Users()));
        dto.setAnnounce(entity.getTitle());
        return dto;
    }
    public Posts mapToPostEntity(PostDTO dto){
        Posts entity = new Posts();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getAnnounce());
        entity.setUser(dto.getUser());
        entity.setTime(dto.getTimestamp());
        return entity;
    }
}
