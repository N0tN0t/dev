package main.entities;

import lombok.Getter;
import lombok.Setter;
import main.dto.UserDTO;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(name = "is_moderator", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isModerator;
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
    public Role getRole(){
        return isModerator == true ? Role.MODERATOR : Role.USER;
    }
}
