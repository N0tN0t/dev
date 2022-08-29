package main.respositories;

import main.entities.PostVotes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends CrudRepository<PostVotes,Integer> {
    @Query(value = "SELECT * FROM post_votes WHERE post_id = :post_id AND user_id = :user_id",nativeQuery = true)
    PostVotes findByPostIdAndUserId(@Param("post_id") int postId,@Param("user_id") int userId);
}
