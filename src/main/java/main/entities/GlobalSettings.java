package main.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.global_settings")
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String value;
}
