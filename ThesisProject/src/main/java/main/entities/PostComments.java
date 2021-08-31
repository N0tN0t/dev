package main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter
    @Setter
    private int id;
    @Getter @Setter
    private int parent_id;
    @NotNull
    @Getter @Setter
    private int post_id;
    @NotNull
    @Getter @Setter
    private int user_id;
    @NotNull
    @Getter @Setter
    private Date time;
    @NotNull
    @Getter @Setter
    private String text;
}
