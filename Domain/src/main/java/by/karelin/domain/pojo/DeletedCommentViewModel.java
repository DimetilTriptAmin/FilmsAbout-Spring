package by.karelin.domain.pojo;

import java.util.Date;

public class DeletedCommentViewModel {
    private Long id;
    private String username;
    private String text;
    private Date publishDate;
    private int rating;

    //region ctors
    public DeletedCommentViewModel() {}
    public DeletedCommentViewModel(Long id, String username, String text, Date publishDate, int rating)
    {
        this.id = id;
        this.username = username;
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
