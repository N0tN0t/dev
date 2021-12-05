package main.entities;

import lombok.Getter;
import lombok.Setter;
import main.ModerationStatus;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static java.time.Instant.now;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    @NotNull
    private Integer is_active;
    @NotNull
    private ModerationStatus moderation_status;
    private Integer moderation_id;
    @NotNull
    private Integer user_id;
    @NotNull
    private Date time = Date.from(now());
    @NotNull
    private String title;
    @NotNull
    private String text;
    @NotNull
    private Integer view_count;
}
