package main.service;

import main.api.response.PostListResponse;
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
    public PostListResponse getPosts(Integer offset, Integer limit, String mode) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (mode) {
            case "popular":
                page = postRepository.findPopularPosts(pageable);
                break;
            case "early":
                page = postRepository.findEarlyPosts(pageable);
                break;
            case "best":
                page = postRepository.findBestPosts(pageable);
                break;
            default:
                page = postRepository.findRecentPosts(pageable);
        }

        PostListResponse apiList = new PostListResponse();
        List<Posts> posts = new ArrayList<>();
        posts.addAll(page.getContent());
        apiList.setCount(page.getTotalElements());

        List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                .collect(Collectors.toList());
        apiList.setPosts(postDtoList);
        return apiList;
    }

    private PostsResponseDTO getPostsResponseDTO(Page<Posts> pagePosts) {
        List<Posts> posts = new ArrayList<>();
        pagePosts.forEach(posts::add);
        List<PostDTO> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(mappingUtils.mapToPostDto(p));
        }
        return new PostsResponseDTO(pagePosts.getTotalElements(), list);
    }
}
