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
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public PostListResponse getPostsWithStatus(Integer offset, Integer limit, String status) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (status) {
            case "new":
                page = postRepository.findNewPosts(pageable);
                break;
            case "declined":
                page = postRepository.findDeclinedPosts(pageable);
                break;
            default:
                page = postRepository.findAcceptedPosts(pageable);
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

    public PostListResponse getMyPosts(int offset, int limit, String status) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (status) {
            case "inactive":
                page = postRepository.findMyInActivePosts(pageable);
                break;
            case "pending":
                page = postRepository.findMyPendingPosts(pageable);
                break;
            case "declined":
                page = postRepository.findMyDeclinedPosts(pageable);
                break;
            default:
                page = postRepository.findMyPublishedPosts(pageable);
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

    public PostListResponse findPostsByQuery(int offset, int limit, String query) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        PostListResponse listResponse = new PostListResponse();
        ArrayList postsList = new ArrayList();
        Long count = null;
        for (Posts post:postRepository.findAll()) {
            if (post.getText().contains(query) || post.getTitle().contains(query)) {
                count++;
                postsList.add(post);
            }
        }
        listResponse.setCount(count);
        listResponse.setPosts(postsList);
        return listResponse;
    }

    public ArrayList calendar(int year) {
        ArrayList result = new ArrayList();
        int[] years = new int[0];
        Map<Date,Integer> posts = null;
        for (Posts post:postRepository.findAll()) {
            boolean db = false;
            for (int year1: years) {
                if (db == false) {
                    if (year1 == post.getTime().getYear()) {
                        db = true;
                        break;
                    }
                }
            }
            if (db == false) {
                years[years.length] = post.getTime().getYear();
            }
            posts.put(post.getTime(),posts.get(post.getTime()).intValue()+1);
        }
        result.add(years);
        result.add(posts);
        return result;
    }

    public PostListResponse findPostsByDate(int offset, int limit, Date date) {
        PostListResponse listResponse = new PostListResponse();
        ArrayList postsList = new ArrayList();
        Long count = null;
        return listResponse;
    }
}
