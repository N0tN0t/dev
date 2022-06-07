package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

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
    private int id;
    @NotNull
    @Column(name = "is_active", columnDefinition = "BIT", nullable = false)
    private int isActive;
    @NotNull
    @Column(name = "moderation_status", columnDefinition = "INT4", nullable = false)
    private ModerationStatus moderationStatus;
    @Column(name = "moderation_id", columnDefinition = "INTEGER", nullable = false)
    private int moderationId;
    @NotNull
    private int user_id;
    @NotNull
    private Date time = Date.from(now());
    @NotNull
    private String title;
    @NotNull
    private String text;
    @NotNull
    @Column(name = "view_count", columnDefinition = "INT4", nullable = false)
    private int viewCount;
}
