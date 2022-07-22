package main.respositories;

import main.entities.Posts;
import main.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Query(value = "SELECT * FROM posts WHERE WHERE is_active = 1 AND AND moderation_status = 'ACCEPTED' AND time <= NOW() AND CONTAINS(text,:query) ORDER BY posts.time DESC",
            nativeQuery = true)
    public Page<Posts> findByQueryPosts(Pageable pageable, @Param("query") String query);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    public Page<Posts> findAcceptedPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'DECLINED' AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    public Page<Posts> findDeclinedPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'NEW' AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    public Page<Posts> findNewPosts(Pageable pageable);

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

    @Query(value = "SELECT * FROM posts WHERE is_active = 0 AND user.id = user_id AND user_id = :id AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findMyInActivePosts(Pageable pageable,@Param("id") Integer id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'NEW' AND user.id = user_id AND user_id = :id AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findMyPendingPosts(Pageable pageable,@Param("id") Integer id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'DECLINED' AND user.id = user_id AND user_id = :id AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findMyDeclinedPosts(Pageable pageable,@Param("id") Integer id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND user.id = user_id AND user_id = :id AND time <= NOW() ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findMyPublishedPosts(Pageable pageable, @Param("id") Integer id);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND time <= NOW() " +
            "AND DATE_FORMAT(posts.time, '%Y-%m-%d') = :date " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Page<Posts> findByDate(Pageable pageable, @Param("date") Date date);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() " +
            "ORDER BY (SELECT COUNT(*) FROM tag2post WHERE post_id = posts.id AND tag_id = :tag) DESC",
            nativeQuery = true)
    Page<Posts> findByTag(Pageable pageable, @Param("tag") Integer tag);

    @Query(value = "SELECT * FROM posts WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' AND posts.time <= NOW() AND posts.id = :id " +
            "ORDER BY posts.time DESC",
            nativeQuery = true)
    Posts findPostById(@Param("id") Integer id);
}
