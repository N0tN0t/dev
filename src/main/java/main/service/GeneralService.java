package main.service;

import main.api.response.StatisticsResponse;
import main.dto.PostDTO;
import main.entities.GlobalSettings;
import main.entities.PostComments;
import main.entities.Posts;
import main.entities.Users;
import main.mappings.PostMappingUtils;
import main.requests.CommentRequest;
import main.requests.ProfileRequest;
import main.respositories.PostCommentsRepository;
import main.respositories.PostRepository;
import main.respositories.SettingsRepository;
import main.respositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.util.*;

@Service
public class GeneralService {
    PostRepository postRepository;
    PostCommentsRepository commentsRepository;
    UserRepository userRepository;
    PostMappingUtils postMappingUtils;
    SettingsRepository settingsRepository;

    public GeneralService(SettingsRepository settingsRepository,PostMappingUtils postMappingUtils,PostRepository postRepository,PostCommentsRepository commentsRepository,UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
        this.userRepository = userRepository;
        this.postMappingUtils = postMappingUtils;
        this.settingsRepository = settingsRepository;
    }

    public ArrayList postImage(MultipartFile image) {
        ArrayList result = new ArrayList();
        int randomHash = new Random().toString().hashCode();
        File dir = new File("/upload"+"/"+String.valueOf(randomHash).substring(0,String.valueOf(randomHash).length()/3)+"/"+String.valueOf(randomHash).substring(String.valueOf(randomHash).length()/3,String.valueOf(randomHash).length()/3*2)+"/"+String.valueOf(randomHash).substring(String.valueOf(randomHash).length()/3*2));
        if (image.getSize()/1000000<8) {
            new File(dir,image.toString());
            result.add(dir.toString());
        }
        else {
            result.add(false);
            HashMap<String,String> errors = new HashMap<>();
            errors.put("image","Размер файла превышает допустимый размер");
            result.add(errors);
        }
        return result;
    }

    public ArrayList postComment(CommentRequest commentRequest) {
        ArrayList result = new ArrayList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashMap<String,String> errors = new HashMap<>();
        if (auth.isAuthenticated()) {
            if (commentRequest.getParentId() != null && postRepository.findPostById(commentRequest.getPostId()) != null && postRepository.findPostById(Integer.valueOf(commentRequest.getParentId())) != null || postRepository.findPostById(commentRequest.getPostId()) != null && commentsRepository.findPostById(commentRequest.getPostId()) != null) {
                System.out.println(commentRequest.getParentId());
                System.out.println(commentRequest.getPostId());
                System.out.println(commentRequest.getText());
                PostComments postComment = new PostComments();
                postComment.setText(commentRequest.getText());
                postComment.setTime(Date.from(Instant.now()));
                if (commentsRepository.findAll().iterator().hasNext()) {
                    postComment.setId(commentsRepository.findAll().iterator().next().getId() + 1);
                } else {
                    postComment.setId(1);
                }
                postComment.setUsers(userRepository.findByEmail(auth.getName()).get());
                if (commentRequest.getParentId() != null) {
                    postComment.setParent_id(Integer.valueOf(commentRequest.getParentId()));
                }
                postComment.setPost(postRepository.findPostById(commentRequest.getPostId()));
                if (postComment.getText().isEmpty() || postComment.getText().length() < 50) {
                    result.add(false);
                    errors.put("text", "Текст комментария не задан или слишком короткий");
                    result.add(errors);
                } else {
                    result.add(postComment.getId());
                    commentsRepository.save(postComment);
                }
            } else {
                result.add(false);
                result.add(errors);
            }
        }
        else {
            result.add(false);
            result.add(errors);
        }
        return result;
    }

    public ArrayList editMyProfile(ProfileRequest profileRequest) {
        ArrayList result = new ArrayList();
        HashMap<String,String> errors = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            Users user = userRepository.findByEmail(auth.getName()).get();
            if (profileRequest.getName() != null) {
                if (profileRequest.getName().length() > 0) {
                    user.setName(profileRequest.getName());
                } else {
                    errors.put("name", "Имя указано неверно");
                }
            }
            if (profileRequest.getPhoto() != null) {
                if (userRepository.findByEmail(profileRequest.getEmail()) == null) {
                    user.setEmail(profileRequest.getEmail());
                } else {
                    errors.put("email", "Этот e-mail уже зарегестрирован");
                }
            }
            if (profileRequest.getPassword() != null) {
                if (profileRequest.getPassword().length() > 6) {
                    if (profileRequest.getPhoto() != null) {
                        if (profileRequest.getPhoto().getTotalSpace() / (1024 * 1024) < 5) {
                            user.setPhoto(profileRequest.getPhoto().toString());
                        } else {
                            errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
                        }
                    }
                    user.setPassword(profileRequest.getPassword());
                } else {
                    errors.put("password", "Пароль короче 6-ти символов");
                }
            }
            if (profileRequest.getRemovePhoto() == 0 && profileRequest.getPhoto() != null) {
                user.setPhoto(profileRequest.getPhoto().toString());
            }
            else {
                user.setPhoto("");
            }
        }
        return result;
    }

    public StatisticsResponse myStatistics() {
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            List<Posts> myPosts = postRepository.findMyPosts(userRepository.findByEmail(auth.getName()).get().getId());
            int postCount = 0;
            int likesCount = 0;
            int dislikesCount = 0;
            int viewsCount = 0;
            Date firstPublication = Date.from(Instant.now());
            for (Posts post:myPosts) {
                postCount += 1;
                viewsCount += post.getViewCount();
                PostDTO dto = postMappingUtils.mapToPostDto(post);
                likesCount += dto.getLikeCount();
                dislikesCount += dto.getDislikeCount();
                if (post.getTime().getTime() < firstPublication.getTime()) {
                    firstPublication = post.getTime();
                }
            }
            statisticsResponse.setViewsCount(viewsCount);
            statisticsResponse.setDislikesCount(dislikesCount);
            statisticsResponse.setLikesCount(likesCount);
            statisticsResponse.setPostsCount(postCount);
            statisticsResponse.setFirstPublication(firstPublication.getTime()/1000);
        }
        return statisticsResponse;
    }

    public Object allStatistics() {
        GlobalSettings settings = settingsRepository.findBySettingsCode("STATISTICS_IS_PUBLIC");
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            if (Boolean.valueOf(settings.getValue()) == false){
                if (userRepository.findByEmail(auth.getName()).get().getIsModerator() == 0) {
                    return HttpStatus.UNAUTHORIZED;
                }
            }
            Date firstPublication = Date.from(Instant.now());
            int postCount = 0;
            int likesCount = 0;
            int dislikesCount = 0;
            int viewsCount = 0;
            for (Users user : userRepository.findAll()) {
                List<Posts> myPosts = postRepository.findMyPosts(user.getId());
                for (Posts post : myPosts) {
                    postCount += 1;
                    viewsCount += post.getViewCount();
                    PostDTO dto = postMappingUtils.mapToPostDto(post);
                    likesCount += dto.getLikeCount();
                    dislikesCount += dto.getDislikeCount();
                    if (post.getTime().getTime() < firstPublication.getTime()) {
                        firstPublication = post.getTime();
                    }
                }
            }
            statisticsResponse.setViewsCount(viewsCount);
            statisticsResponse.setDislikesCount(dislikesCount);
            statisticsResponse.setLikesCount(likesCount);
            statisticsResponse.setPostsCount(postCount);
            statisticsResponse.setFirstPublication(firstPublication.getTime() / 1000);
        }
        return statisticsResponse;
    }

    public ArrayList editMyProfilePhoto(MultipartFile photo, String name, String email, String password) {
        ArrayList result = new ArrayList();
        HashMap<String,String> errors = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            Users user = userRepository.findByEmail(auth.getName()).get();
            if (name != null) {
                if (name.length() > 0) {
                    user.setName(name);
                } else {
                    errors.put("name", "Имя указано неверно");
                }
            }
            if (email != null) {
                if (userRepository.findByEmail(email) == null) {
                    user.setEmail(email);
                } else {
                    errors.put("email", "Этот e-mail уже зарегестрирован");
                }
            }
            if (password != null) {
                if (password.length() > 6) {
                    if (photo != null) {
                        if (photo.getSize() / 1000000 < 5) {
                            user.setPhoto(photo.toString());
                        } else {
                            errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
                        }
                    }
                    user.setPassword(password);
                } else {
                    errors.put("password", "Пароль короче 6-ти символов");
                }
            }
            if (photo != null) {
                if (photo.getSize() * 1000000 < 5) {
                    user.setPhoto(photo.toString());
                } else {
                    errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
                }
            }
        }
        return result;
    }
}
