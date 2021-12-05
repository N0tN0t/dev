package main.entities;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "captcha_code")
@PropertySource("classpath:my_blog.captcha_codes")
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    @NotNull
    private Date time;
    @NotNull
    private String code;
    @NotNull
    private String secret_code;
}
