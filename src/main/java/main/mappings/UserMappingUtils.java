package main.mappings;

import main.dto.UserDTO;
import main.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMappingUtils {
    public UserDTO mapToPostDto(User entity){
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPhoto(entity.getPhoto());
        dto.setModeration(entity.isModerator());
        return dto;
    }
    public User mapToPostEntity(UserDTO dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setId(dto.getId());
        user.setPhoto(dto.getPhoto());
        user.setModerator(dto.isModeration());
        user.setName(dto.getName());
        return user;
    }
}
