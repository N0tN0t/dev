package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@PropertySource("classpath:my_blog.post_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;
    @Column(columnDefinition = "DATE", nullable = false)
    private Date time;
    private int value;
}
