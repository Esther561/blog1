package ictgradschool.project.model;

import ictgradschool.project.util.SanitizationUtil;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Comment implements Serializable {
    private Integer id;
    private String content;
    private Timestamp time;
    private Integer article;
    private Integer parent;
    private User parentUser;
    private int likes = 0;
    private int level = 0;
    private User user;
    private List<Comment> comments;


    public Comment(Integer id, String content, Timestamp time, User user, Integer article, Integer parent) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.user = user;
        this.article = article;
        this.parent = parent;
    }

    public Comment(Integer id, String content, Timestamp time, Integer article, int parent, User parentUser, int likes, int level, User user, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.article = article;
        this.parent = parent;
        this.parentUser = parentUser;
        this.likes = likes;
        this.level = level;
        this.user = user;
        this.comments = comments;
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = SanitizationUtil.sanitize(content);
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }
}
