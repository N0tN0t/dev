package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static java.time.Instant.now;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(name = "is_active", columnDefinition = "BIT", nullable = false)
    private int isActive;
    @Column(name = "moderation_status", columnDefinition = "INT4", nullable = false)
    private ModerationStatus moderationStatus;
    @Column(name = "moderation_id", columnDefinition = "INTEGER", nullable = false)
    private int moderationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(columnDefinition = "DATE", nullable = false)
    private Date time = Date.from(now());
    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;
    @Column(name = "view_count", columnDefinition = "INT4", nullable = false)
    private int viewCount;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostVotes> postVotes;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComments> postComments;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<Tags> tags;
}
