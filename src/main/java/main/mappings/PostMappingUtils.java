package main.mappings;

import main.dto.PostDTO;
import main.entities.PostVotes;
import main.entities.Posts;
import main.entities.Users;
import main.respositories.PostCommentsRepository;
import main.respositories.PostVotesRepository;
import main.respositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostMappingUtils {
    UserRepository userRepository;
    UserMappingUtils userMappingUtils;
    PostVotesRepository postVotesRepository;
    PostCommentsRepository postCommentsRepository;

    public PostMappingUtils(PostCommentsRepository postCommentsRepository, PostVotesRepository postVotesRepository, UserRepository userRepository, UserMappingUtils userMappingUtils) {
        this.userRepository = userRepository;
        this.userMappingUtils = userMappingUtils;
        this.postVotesRepository = postVotesRepository;
        this.postCommentsRepository = postCommentsRepository;
    }

    public PostDTO mapToPostDto(Posts entity) {
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setTimestamp(entity.getTime().getTime() / 1000);
        dto.setUser(userMappingUtils.mapToPostDto(userRepository.findById(entity.getUsers().getId()).orElse(new Users())));
        dto.setAnnounce(entity.getTitle());
        dto.setViewCount(entity.getViewCount());
        int likes = 0;
        int dislikes = 0;
        if (postVotesRepository.findByPostIdAndUserId(entity.getId(),entity.getUsers().getId()).getPost().getPostVotes() != null) {
            for (PostVotes postVote : postVotesRepository.findByPostIdAndUserId(entity.getId(), entity.getUsers().getId()).getPost().getPostVotes()) {
                if (postVote.getValue() == 1) {
                    likes += 1;
                } else {
                    dislikes += 1;
                }
            }
        }
        dto.setCommentCount(entity.getPostComments().size());
        dto.setLikeCount(likes);
        dto.setDislikeCount(dislikes);
        return dto;
    }

    public Posts mapToPostEntity(PostDTO dto) {
        Posts entity = new Posts();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getAnnounce());
        entity.setUsers(userMappingUtils.mapToPostEntity(dto.getUser()));
        entity.setTime(new Date(dto.getTimestamp()));
        entity.setViewCount(dto.getViewCount());
        entity.setPostComments(postCommentsRepository.findPostById(dto.getId()));
        return entity;
    }
}
