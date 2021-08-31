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
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter @Setter
    private int id;
    @NotNull
    @Getter @Setter
    private int is_active;
    @NotNull
    @Getter @Setter
    private ModerationStatus moderation_status;
    @Getter @Setter
    private int moderation_id;
    @NotNull
    @Getter @Setter
    private int user_id;
    @NotNull
    @Getter @Setter
    private Date time = Date.from(now());
    @NotNull
    @Getter @Setter
    private String title;
    @NotNull
    @Getter @Setter
    private String text;
    @NotNull
    @Getter
    @Setter
    private int view_count;
}
