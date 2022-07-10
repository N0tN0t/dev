package main.respositories;

import main.entities.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer> {
    @Query(value = "SELECT COUNT(*) FROM Post p " +
            "WHERE p.isActive = 1 " +
            "AND p.moderationStatus = 'ACCEPTED' " +
            "AND p.time <= CURRENT_TIMESTAMP()")
    Optional<Integer> countAllActiveAndAccepted();

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY time DESC",
            nativeQuery = true)
    public Page<Posts> findRecentPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY (SELECT COUNT(*) FROM post_votes WHERE post_id = posts.id) DESC",
            nativeQuery = true)
    public Page<Posts> findBestPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY posts.time ASC",
            nativeQuery = true)
    public Page<Posts> findEarlyPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY (SELECT COUNT(*) FROM post_comments WHERE post_id = posts.id) DESC",
            nativeQuery = true)
    public Page<Posts> findPopularPosts(Pageable pageable);

}
