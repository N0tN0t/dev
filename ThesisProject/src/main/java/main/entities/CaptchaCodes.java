package main.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter
    @Setter
    private int id;
    @NotNull
    @Getter @Setter
    private Date time;
    @NotNull
    @Getter @Setter
    @Column(name = "code", columnDefinition = "TINYTEXT")
    private String code;
    @NotNull
    @Getter @Setter
    @Column(name = "secret_code", columnDefinition = "TINYTEXT")
    private String secret_code;
}
