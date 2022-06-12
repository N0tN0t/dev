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
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(columnDefinition = "DATE", nullable = false)
    private Date time;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    @Column(name = "secret_code", columnDefinition = "TINYTEXT", nullable = false)
    private String secretCode;
}
