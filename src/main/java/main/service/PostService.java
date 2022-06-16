package main.service;

import main.mappings.PostMappingUtils;
import main.dto.PostDTO;
import main.entities.Posts;
import main.respositories.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private PostRepository postRepository;
    private PostMappingUtils mappingUtils;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }
    public PostDTO findById(Integer id){
        return mappingUtils.mapToPostDto(postRepository.findById(id).orElse(new Posts()));
    }
    public boolean getPosts(Integer offset, Integer limit, String mode) {
        return postRepository.findAll().subList(offset,offset+limit).contains(mode);
    }
}
