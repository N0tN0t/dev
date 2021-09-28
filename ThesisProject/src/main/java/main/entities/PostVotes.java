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
@Getter @Setter
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private int user_id;
    @NotNull
    private int post_id;
    @NotNull
    private Date time;
    private int value;
}
