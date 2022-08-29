package main.service;

import main.entities.PostComments;
import main.entities.Users;
import main.requests.CommentRequest;
import main.requests.ProfileRequest;
import main.respositories.PostCommentsRepository;
import main.respositories.PostRepository;
import main.respositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.util.*;

@Service
public class GeneralService {
    PostRepository postRepository;
    PostCommentsRepository commentsRepository;
    UserRepository userRepository;

    public GeneralService(PostRepository postRepository,PostCommentsRepository commentsRepository,UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
        this.userRepository = userRepository;
    }

    public ArrayList postImage(File image) {
        ArrayList result = new ArrayList();
        int randomHash = new Random().toString().hashCode();
        File dir = new File("/upload"+"/"+String.valueOf(randomHash).substring(0,String.valueOf(randomHash).length()/3)+"/"+String.valueOf(randomHash).substring(String.valueOf(randomHash).length()/3,String.valueOf(randomHash).length()/3*2)+"/"+String.valueOf(randomHash).substring(String.valueOf(randomHash).length()/3*2));
        if (image.getTotalSpace()>1000) {
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
            System.out.println(userRepository.findByEmail(auth.getName()));
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
}
