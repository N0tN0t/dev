package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.post_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    @Column(name = "user_id", columnDefinition = "INTEGER", nullable = false)
    private int userId;
    @NotNull
    @Column(name = "post_id", columnDefinition = "INTEGER", nullable = false)
    private int postId;
    @NotNull
    private Date time;
    private int value;
}
