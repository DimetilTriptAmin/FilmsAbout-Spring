package by.karelin.business.dto.Responses;

import java.util.Date;

public class CommentResponse {
    private Long id;
    private String username;
    private String avatar;
    private String text;
    private Date publishDate;
    private int rating;

    //region ctors
    public CommentResponse() {}
    public CommentResponse(Long id, String username, String avatar, String text, Date publishDate, int rating)
    {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.text = text;
        this.rating = rating;
        this.publishDate = publishDate;
    }
    //endregion

    //region getters&setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    //endregion
}
