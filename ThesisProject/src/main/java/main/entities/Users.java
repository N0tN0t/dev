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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter @Setter
    private int id;
    @NotNull
    @Getter @Setter
    private int is_moderator;
    @NotNull
    @Getter @Setter
    private Date reg_time;
    @NotNull
    @Getter @Setter
    private String name;
    @NotNull
    @Getter @Setter
    private String email;
    @NotNull
    @Getter @Setter
    private String password;
    @NotNull
    @Getter @Setter
    private String code;
    @NotNull
    @Getter @Setter
    private String photo;

}
