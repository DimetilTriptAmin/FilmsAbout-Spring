package by.karelin.domain.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FA_COMMENT")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    private Film film;

    public Comment() {}

    //region getters&setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public by.karelin.domain.models.User getUser() {
        return user;
    }

    public void setUser(by.karelin.domain.models.User user) {
        this.user = user;
    }

    public by.karelin.domain.models.Film getFilm() {
        return film;
    }

    public void setFilm(by.karelin.domain.models.Film film) {
        this.film = film;
    }

    //endregion
}
