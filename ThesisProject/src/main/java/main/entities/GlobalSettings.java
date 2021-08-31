package main.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter
    @Setter
    private int id;
    @NotNull
    @Getter @Setter
    private String code;
    @NotNull
    @Getter @Setter
    private String name;
    @NotNull
    @Getter @Setter
    private String value;
}
