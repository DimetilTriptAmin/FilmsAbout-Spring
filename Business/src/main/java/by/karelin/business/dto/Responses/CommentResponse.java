package by.karelin.business.dto.Responses;

import java.util.Date;

public class CommentResponse {
    private Long id;
    private String userName;
    private String avatar;
    private String text;
    private Date publishDate;
    private int rating;

    //region ctors
    public CommentResponse(String userName, String avatar, String text, int rating, Date publishDate)
    {
        this.userName = userName;
        this.avatar = avatar;
        this.text = text;
        this.rating = rating;
        this.publishDate = publishDate;
    }
    public CommentResponse(Long id, String userName, String avatar, String text, int rating, Date publishDate)
    {
        this.id = id;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
