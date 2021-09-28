package main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
public class Tag2Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private int post_id;
    @NotNull
    private int tag_id;
}
