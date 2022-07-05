package main.respositories;

import main.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tags,Integer> {
}
