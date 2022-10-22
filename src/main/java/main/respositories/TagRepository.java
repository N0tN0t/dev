package main.respositories;

import main.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {
    @Query(value = "SELECT * FROM tags WHERE tags.name = :tag ",
            nativeQuery = true)
    Tags findTagByName(@Param("tag") String tag);
}
