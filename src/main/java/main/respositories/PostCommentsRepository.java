package main.respositories;

import main.entities.PostComments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentsRepository extends CrudRepository<PostComments,Integer> {
    @Query(value = "SELECT * FROM post_comments WHERE post_id = :id",
            nativeQuery = true)
    PostComments findPostById(@Param("id") int id);
}
