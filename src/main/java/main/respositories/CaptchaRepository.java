package main.respositories;

import main.entities.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptchaRepository  extends JpaRepository<CaptchaCodes,Integer> {
    @Query(value = "SELECT * FROM captcha_code WHERE time > now() + interval '1 hour'",nativeQuery = true)
    Iterable<? extends CaptchaCodes> findOldCaptcha();

    @Query(value = "SELECT * FROM captcha_code",nativeQuery = true)
    List<CaptchaCodes> findAllCaptcha();
}
