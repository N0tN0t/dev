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
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter
    @Setter
    private int id;
    @NotNull
    @Getter @Setter
    private int user_id;
    @NotNull
    @Getter @Setter
    private int post_id;
    @NotNull
    @Getter @Setter
    private Date time;
    @Getter @Setter
    private int value;
}
