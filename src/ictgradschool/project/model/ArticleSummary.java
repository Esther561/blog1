package ictgradschool.project.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ArticleSummary implements Serializable {
    private int id;
    private String title;
    private String contentSummary;
    private String cover;
    private User user;
    private Timestamp time;
    private int likes;
    private List<String> tags;

    public ArticleSummary(int id, String title, String contentSummary, String cover, User user, Timestamp time, int likes, List<String> tags) {
        this.id = id;
        this.title = title;
        this.contentSummary = contentSummary;
        this.cover = cover;
        this.user = user;
        this.time = time;
        this.likes = likes;
        this.tags = tags;
    }

    public ArticleSummary() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentSummary() {
        return contentSummary;
    }

    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}