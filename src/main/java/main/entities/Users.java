package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    @Column(name = "is_moderator", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isModerator;
    @NotNull
    @Column(name = "reg_time", columnDefinition = "DATE", nullable = false)
    private Date regTime;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String code;
    @NotNull
    private String photo;

}
