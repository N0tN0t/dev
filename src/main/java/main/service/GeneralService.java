package main.service;

import main.entities.PostComments;
import main.requests.CommentRequest;
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
        if (postRepository.findPostById(commentRequest.getPost_id()) != null && postRepository.findPostById(commentRequest.getParent_id()) != null || postRepository.findPostById(commentRequest.getPost_id()) != null && commentsRepository.findPostById(commentRequest.getPost_id()) != null) {
            PostComments postComment = new PostComments();
            postComment.setText(commentRequest.getText());
            postComment.setTime(Date.from(Instant.now()));
            if (commentsRepository.findAll().iterator().hasNext()) {
                postComment.setId(commentsRepository.findAll().iterator().next().getId()+1);
            }else {
                postComment.setId(1);
            }
            postComment.setUsers(userRepository.findByEmail(auth.getName()).get());
            postComment.setParent_id(commentRequest.getParent_id());
            if (postRepository.findPostById(commentRequest.getParent_id()) != null) {
                postComment.setPost(postRepository.findPostById(commentRequest.getParent_id()));
            }
            else {
                postComment.setPost(commentsRepository.findCommentById(postComment.getParent_id()).getPost());
            }
            if (postComment.getText().isEmpty() || postComment.getText().length() < 50) {
                result.add(false);
                errors.put("text","Текст комментария не задан или слишком короткий");
                result.add(errors);
            }
            else {
                result.add(postComment.getId());
                commentsRepository.save(postComment);
            }
        }
        else {
            result.add(false);
            result.add(errors);
        }
        return result;
    }
}
