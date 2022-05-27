package main.service;

import main.dto.PostDTO;
import main.dto.UserDTO;
import main.entities.Posts;
import main.entities.Users;
import main.mappings.UserMappingUtils;
import main.respositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private UserRepository userRepository;
    private UserMappingUtils mappingUtils;
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }
    public UserDTO findById(Integer id){
        return mappingUtils.mapToPostDto(userRepository.findById(id).orElse(new Users()));
    }
}
