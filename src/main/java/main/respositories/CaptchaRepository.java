package main.respositories;

import main.entities.CaptchaCodes;
import main.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaRepository  extends JpaRepository<CaptchaCodes,Integer> {
}
