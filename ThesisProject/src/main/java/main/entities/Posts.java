package main.entities;

import main.ModerationStatus;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private boolean is_active;
    @NotNull
    private ModerationStatus moderation_status;
    private int moderation_id;
    @NotNull
    private int user_id;
    @NotNull
    private Date time;
    @NotNull
    private String title;
    @NotNull
    private String text;
    @NotNull
    private int view_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public ModerationStatus getModeration_status() {
        return moderation_status;
    }

    public void setModeration_status(ModerationStatus moderation_status) {
        this.moderation_status = moderation_status;
    }

    public int getModeration_id() {
        return moderation_id;
    }

    public void setModeration_id(int moderation_id) {
        this.moderation_id = moderation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }
}
