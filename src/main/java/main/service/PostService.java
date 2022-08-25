package main.service;

import main.api.response.CalendarResponse;
import main.api.response.PostByIdResponse;
import main.api.response.PostListResponse;
import main.dto.CommentDTO;
import main.dto.PostsResponseDTO;
import main.dto.UserDTO;
import main.entities.PostComments;
import main.entities.Tags;
import main.entities.Users;
import main.mappings.CommentMappingUtils;
import main.mappings.PostMappingUtils;
import main.dto.PostDTO;
import main.entities.Posts;
import main.mappings.UserMappingUtils;
import main.requests.PostRequest;
import main.respositories.PostCommentsRepository;
import main.respositories.PostRepository;
import main.respositories.TagRepository;
import main.respositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private PostCommentsRepository postCommentsRepository;
    private TagRepository tagRepository;
    private PostMappingUtils mappingUtils;
    private UserMappingUtils userMappingUtils;
    private CommentMappingUtils commentMappingUtils;
    private UserService userService;
    private AuthCheckService checkService;
    private UserRepository userRepository;

    public PostService(UserRepository userRepository,CommentMappingUtils commentMappingUtils,UserMappingUtils userMappingUtils,AuthCheckService checkService,PostRepository postRepository,PostCommentsRepository postCommentsRepository,TagRepository tagRepository,PostMappingUtils mappingUtils,UserService userService) {
        this.postRepository = postRepository;
        this.postCommentsRepository = postCommentsRepository;
        this.tagRepository = tagRepository;
        this.mappingUtils = mappingUtils;
        this.userService = userService;
        this.userMappingUtils = userMappingUtils;
        this.commentMappingUtils = commentMappingUtils;
        this.checkService = checkService;
        this.userRepository = userRepository;
    }

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PostListResponse apiList = new PostListResponse();
        if (auth.isAuthenticated()) {
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


            List<Posts> posts = new ArrayList<>();
            posts.addAll(page.getContent());
            apiList.setCount(page.getTotalElements());

            List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                    .collect(Collectors.toList());
            apiList.setPosts(postDtoList);
        }
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
        PostListResponse apiList = new PostListResponse();
        if (auth.isAuthenticated()) {
            switch (status) {
                case "inactive":
                    page = postRepository.findMyInActivePosts(pageable, userService.findByEmail(auth.getName()).getId());
                    break;
                case "pending":
                    page = postRepository.findMyPendingPosts(pageable, userService.findByEmail(auth.getName()).getId());
                    break;
                case "declined":
                    page = postRepository.findMyDeclinedPosts(pageable, userService.findByEmail(auth.getName()).getId());
                    break;
                default:
                    page = postRepository.findMyPublishedPosts(pageable, userService.findByEmail(auth.getName()).getId());
            }


            List<Posts> posts = new ArrayList<>();
            posts.addAll(page.getContent());
            apiList.setCount(page.getTotalElements());

            List<PostDTO> postDtoList = posts.stream().map(mappingUtils::mapToPostDto)
                    .collect(Collectors.toList());
            apiList.setPosts(postDtoList);
        }
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
        Map<String,Integer> posts = new HashMap<>();
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
            if (db == false && years.length>0) {
                years[years.length] = String.valueOf(post.getTime().getYear());
            }
            if (posts.get(post.getTime()) != null) {
                posts.put(String.valueOf(post.getTime()), posts.get(post.getTime()).intValue() + 1);
            }
            else {
                posts.put(String.valueOf(post.getTime()), 0);
            }
        }
        CalendarResponse calendarResponse = new CalendarResponse(years,posts);
        return calendarResponse;
    }

    public PostByIdResponse findPostsById(int id) {
        Posts post = postRepository.findPostById(id);
        PostDTO postDTO = mappingUtils.mapToPostDto(post);
        UserDTO userDTO = userMappingUtils.mapToPostDto(post.getUsers());
        PostByIdResponse postByIdResponse = null;
        postByIdResponse.setActive(post.getIsActive() == 1 ? true : false);
        postByIdResponse.setId(post.getId());
        postByIdResponse.setTitle(post.getTitle());
        postByIdResponse.setText(post.getText());
        postByIdResponse.setTimestamp(post.getTime().getTime());
        postByIdResponse.setUser(userDTO);
        List<CommentDTO> commentslist = null;
        for (PostComments postComments:postCommentsRepository.findPostById(post.getId())) {
            commentslist.add(commentMappingUtils.mapToPostDto(postComments));
        }
        postByIdResponse.setComments(commentslist);
        postByIdResponse.setDislikeCount(postDTO.getDislikeCount());
        postByIdResponse.setLikeCount(postDTO.getLikeCount());
        List<String> tagslist = null;
        for (Tags tag:post.getTags()) {
            tagslist.add(tag.toString());
        }
        postByIdResponse.setTags(tagslist);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            UserDTO user = userService.findByEmail(auth.getName());
            if (user != null) {
                if (user.getModeration() != 1 && user.getId() != post.getUsers().getId()) {
                    post.setViewCount(post.getViewCount()+1);
                    postRepository.save(post);
                }
            }else {
                post.setViewCount(post.getViewCount()+1);
                postRepository.save(post);
            }
        }
        return postByIdResponse;
    }

    public ArrayList editPost(PostRequest postRequest) {
        Posts post = new Posts();
        List<String> errors = new ArrayList();
        ArrayList response = new ArrayList();
        Date newDate = new Date();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newDate.setTime(postRequest.getTimestamp());
        if (postRepository.findByDateTitle(newDate,postRequest.getTitle()) != null) {
            if (postRequest.getTitle().length() > 3) {
                if (postRequest.getText().length() > 50) {
                    post.setId(postRepository.findAll().iterator().next().getId() + 1);
                    post.setTime(newDate);
                    post.setTitle(postRequest.getTitle());
                    post.setText(postRequest.getText());
                    String email = auth.getName();
                    Users user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new NoSuchElementException("user " + email + " not found"));
                    post.setUsers(user);
                    List<Tags> tags = new ArrayList<>();
                    postRequest.getTags().forEach(i -> {tags.add(tagRepository.findTagByName(i));});
                    post.setTags(tags);
                    post.setIsActive(postRequest.isActive() ? 1 : 0);
                    post.setModerationStatus("NEW");
                }
            }
        }
        if (postRepository.findByDateTitle(newDate,postRequest.getTitle()) == null) {
            errors.add("Пост не найден");
        }
        if (postRequest.getTitle().length()<=3) {
            errors.add("Заголовок не установлен");
        }
        if (postRequest.getText().length() <= 50) {
            errors.add("Текст публикации слишком короткий");
        }
        if (errors.isEmpty()) {
            postRepository.save(post);
            response.add(true);
        }
        else {
            response.add(false);
            response.add(errors);
        }
        return response;
    }

    public ArrayList postPost(PostRequest postRequest) {
        Posts post = new Posts();
        List<String> errors = new ArrayList();
        ArrayList response = new ArrayList();
        Date newDate = new Date();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        newDate.setTime(postRequest.getTimestamp());
        if (postRequest.getTitle().length()>3) {
            if (postRequest.getText().length() > 50) {
                post.setId(postRepository.findAll().iterator().next().getId()+1);
                post.setTime(newDate);
                post.setTitle(postRequest.getTitle());
                post.setText(postRequest.getText());
                post.setModerationStatus("NEW");
                String email = auth.getName();
                Users user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new NoSuchElementException("user " + email + " not found"));
                post.setUsers(user);
                List<Tags> tags = new ArrayList<>();
                postRequest.getTags().forEach(i -> {tags.add(tagRepository.findTagByName(i));});
                post.setTags(tags);
                post.setIsActive(postRequest.isActive() ? 1 : 0);
            }
        }
        if (postRequest.getTitle().length()<=3) {
            errors.add("Заголовок не установлен");
        }
        if (postRequest.getText().length() <= 50) {
            errors.add("Текст публикации слишком короткий");
        }
        if (errors.isEmpty()) {
            postRepository.save(post);
            response.add(true);
        }
        else {
            response.add(false);
            response.add(errors);
        }
        return response;
    }
}
