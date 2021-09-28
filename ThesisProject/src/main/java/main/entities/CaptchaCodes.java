package main.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private Date time;
    @NotNull
    @Column(name = "code", columnDefinition = "TINYTEXT")
    private String code;
    @NotNull
    @Column(name = "secret_code", columnDefinition = "TINYTEXT")
    private String secret_code;
}
