package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.post_comments")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    private Integer parent_id;
    @NotNull
    private Integer post_id;
    @NotNull
    private Integer user_id;
    @NotNull
    private Date time;
    @NotNull
    private String text;
}
