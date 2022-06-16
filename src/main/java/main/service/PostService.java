package main.service;

import main.api.response.PostInfoResponse;
import main.dto.PostsResponseDTO;
import main.mappings.PostMappingUtils;
import main.dto.PostDTO;
import main.entities.Posts;
import main.respositories.PostInfoRepository;
import main.respositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private PostRepository postRepository;
    private PostInfoRepository postInfoRepository;
    private PostMappingUtils mappingUtils;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(mappingUtils::mapToPostDto).collect(Collectors.toList());
    }
    public PostDTO findById(Integer id){
        return mappingUtils.mapToPostDto(postRepository.findById(id).orElse(new Posts()));
    }
    public boolean getPosts(Integer offset, Integer limit, String mode) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> pagePosts = postInfoRepository.findAllOrderByTimeDesc(pageable);
        switch (mode) {
            case early:
                pagePosts = postInfoRepository.findALLOrderByTimeAsc(pageable);
                break;
            case popular:
                pagePosts = postInfoRepository.findALLOrderByPostCommentsDesc(pageable);
                break;
            case best:
                pagePosts = postInfoRepository.findALLOrderByPostVotes(pageable);
                break;
        }
        return getPostsResponseDTO(pagePosts);
    }

    private PostsResponseDTO getPostsResponseDTO(Page<Posts> pagePosts) {
        List<Posts> posts = new ArrayList<>();
        pagePosts.forEach(posts::add);
        List<PostInfoResponse> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(converter.convertPostToDTO(p));
        }
        return new PostsResponseDTO(pagePosts.getTotalElements(), list);
    }
}
