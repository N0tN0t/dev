package main.mappings;

import main.dto.UserDTO;
import main.entities.Users;
import org.springframework.stereotype.Service;

@Service
public class UserMappingUtils {
    public UserDTO mapToPostDto(Users entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPhoto(entity.getPhoto());
        dto.setModeration(entity.getIsModerator());
        return dto;
    }

    public Users mapToPostEntity(UserDTO dto) {
        Users users = new Users();
        users.setEmail(dto.getEmail());
        users.setId(dto.getId());
        users.setPhoto(dto.getPhoto());
        users.setIsModerator(dto.getModeration());
        users.setName(dto.getName());
        return users;
    }
}
