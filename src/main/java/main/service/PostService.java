package main.service;

import main.api.response.PostInfoResponse;
import main.dto.PostsResponseDTO;
import main.mappings.PostMappingUtils;
import main.dto.PostDTO;
import main.entities.Posts;
import main.respositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
    public PostsResponseDTO getPosts(Integer offset, Integer limit, String mode) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> pagePosts = postRepository.findRecentPosts(pageable);
        switch (mode) {
            case "early":
                pagePosts = postRepository.findEarlyPosts(pageable);
                break;
            case "popular":
                pagePosts = postRepository.findPopularPosts(pageable);
                break;
            case "best":
                pagePosts = postRepository.findBestPosts(pageable);
                break;
        }
        return getPostsResponseDTO(pagePosts);
    }

    private PostsResponseDTO getPostsResponseDTO(Page<Posts> pagePosts) {
        List<Posts> posts = new ArrayList<>();
        pagePosts.forEach(posts::add);
        List<PostInfoResponse> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(mappingUtils.mapToPostDto(p));
        }
        return new PostsResponseDTO(pagePosts.getTotalElements(), list);
    }
}
