package main.entities;

import lombok.Getter;
import lombok.Setter;
import main.ModerationStatus;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static java.time.Instant.now;

@Entity
@Getter @Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private int is_active;
    @NotNull
    private ModerationStatus moderation_status;
    private int moderation_id;
    @NotNull
    private int user_id;
    @NotNull
    private Date time = Date.from(now());
    @NotNull
    private String title;
    @NotNull
    private String text;
    @NotNull
    private int view_count;
}
