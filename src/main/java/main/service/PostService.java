package main.service;

import main.api.response.CalendarResponse;
import main.api.response.PostByIdResponse;
import main.api.response.PostListResponse;
import main.dto.PostsResponseDTO;
import main.entities.PostComments;
import main.entities.Tags;
import main.mappings.PostMappingUtils;
import main.dto.PostDTO;
import main.entities.Posts;
import main.respositories.PostCommentsRepository;
import main.respositories.PostRepository;
import main.respositories.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostService {
    private PostRepository postRepository;
    private PostCommentsRepository postCommentsRepository;
    private TagRepository tagRepository;
    private PostMappingUtils mappingUtils;
    private UserService userService;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        switch (status) {
            case "inactive":
                page = postRepository.findMyInActivePosts(pageable,userService.findByEmail(auth.getName()).getId());
                break;
            case "pending":
                page = postRepository.findMyPendingPosts(pageable,userService.findByEmail(auth.getName()).getId());
                break;
            case "declined":
                page = postRepository.findMyDeclinedPosts(pageable,userService.findByEmail(auth.getName()).getId());
                break;
            default:
                page = postRepository.findMyPublishedPosts(pageable,userService.findByEmail(auth.getName()).getId());
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
        Page<Posts> page = postRepository.findByQueryPosts(pageable,query);
        PostListResponse listResponse = new PostListResponse();
        List<Posts> posts = new ArrayList<>();
        posts.addAll(page.getContent());
        List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                .collect(Collectors.toList());
        listResponse.setCount(page.getTotalElements());
        listResponse.setPosts(postDtoList);
        return listResponse;
    }

    public PostListResponse findPostsByDate(int offset, int limit, Date date) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postRepository.findByDate(pageable,date);
        PostListResponse listResponse = new PostListResponse();
        List<Posts> posts = new ArrayList<>();
        posts.addAll(page.getContent());
        List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                .collect(Collectors.toList());
        listResponse.setCount(page.getTotalElements());
        listResponse.setPosts(postDtoList);
        return listResponse;
    }

    public PostListResponse findPostsByTag(int offset, int limit, String tag) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Tags Tag = tagRepository.findTagByName(tag);
        Page<Posts> page = postRepository.findByTag(pageable,Tag.getId());
        PostListResponse listResponse = new PostListResponse();
        List<Posts> posts = new ArrayList<>();
        posts.addAll(page.getContent());
        List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                .collect(Collectors.toList());
        listResponse.setCount(page.getTotalElements());
        listResponse.setPosts(postDtoList);
        return listResponse;
    }

    public CalendarResponse calendar() {
        String[] years = new String[0];
        Map<String,Integer> posts = null;
        for (Posts post:postRepository.findAll()) {
            boolean db = false;
            for (String year1: years) {
                if (db == false) {
                    if (year1 == String.valueOf(post.getTime().getYear())) {
                        db = true;
                        break;
                    }
                }
            }
            if (db == false) {
                years[years.length] = String.valueOf(post.getTime().getYear());
            }
            posts.put(String.valueOf(post.getTime()),posts.get(post.getTime()).intValue()+1);
        }
        CalendarResponse calendarResponse = new CalendarResponse(years,posts);
        return calendarResponse;
    }

    public PostByIdResponse findPostsById(int id) {
        Posts post = postRepository.findPostById(id);
        PostDTO postDTO = mappingUtils.mapToPostDto(post);
        PostByIdResponse postByIdResponse = new PostByIdResponse();
        postByIdResponse.setActive(post.getIsActive());
        postByIdResponse.setId(post.getId());
        postByIdResponse.setTitle(post.getTitle());
        postByIdResponse.setText(post.getText());
        postByIdResponse.setTimestamp(post.getTime());
        postByIdResponse.setUser(post.getUser());
        postByIdResponse.setComments(postCommentsRepository.findPostById(post.getId()));
        postByIdResponse.setDislikeCount(postDTO.getDislikeCount());
        postByIdResponse.setLikeCount(postDTO.getLikeCount());
        postByIdResponse.setTags(post.getTags());
        if (post.getUser().isModerator()) {
            postByIdResponse.setViewCount(0);
        }
        else if (SecurityContextHolder.getContext().getAuthentication() == post.getUser()) {
            postByIdResponse.setViewCount(0);
        }
        else{
            postByIdResponse.setViewCount(post.getViewCount());
        }
        return postByIdResponse;
    }
}
