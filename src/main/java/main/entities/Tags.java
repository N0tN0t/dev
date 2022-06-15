package main.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter
@PropertySource("classpath:my_blog.tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "Integer", nullable = false)
    private int id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    List<Posts> posts;
}
