package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@PropertySource("classpath:my_blog.users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(name = "is_moderator", columnDefinition = "SMALLINT", nullable = false)
    private short isModerator;
    @Column(name = "reg_time", columnDefinition = "DATE", nullable = false)
    private Date regTime;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String photo;

    public Role getRole() {
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }
}
