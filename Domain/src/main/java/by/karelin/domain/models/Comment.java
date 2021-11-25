package by.karelin.domain.models;

import by.karelin.domain.pojo.CommentViewModel;
import by.karelin.domain.pojo.DeletedCommentViewModel;

import javax.persistence.*;
import java.util.Date;
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "CommentViewModelMapping",
                classes = @ConstructorResult(targetClass = CommentViewModel.class,
                        columns = {
                                @ColumnResult(name = "ID", type = Long.class),
                                @ColumnResult(name = "NAME", type = String.class),
                                @ColumnResult(name = "AVATAR", type = String.class),
                                @ColumnResult(name = "TEXT", type = String.class),
                                @ColumnResult(name = "PUBLISH_DATE", type = Date.class),
                                @ColumnResult(name = "RATE", type = int.class)}
                )
        ),
        @SqlResultSetMapping(name = "DeletedCommentViewModelMapping",
                classes = @ConstructorResult(targetClass = DeletedCommentViewModel.class,
                        columns = {
                                @ColumnResult(name = "ID", type = Long.class),
                                @ColumnResult(name = "NAME", type = String.class),
                                @ColumnResult(name = "TEXT", type = String.class),
                                @ColumnResult(name = "PUBLISH_DATE", type = Date.class),
                                @ColumnResult(name = "RATE", type = int.class)}
                )
        )
})

@Entity
@Table(name = "FA_COMMENT")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Date publishDate;
    private Integer isDeleted;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    private Film film;

    public Comment() {}
    public Comment(User user, Film film, String text) {
        this.user = user;
        this.film = film;
        this.text = text;
        this.isDeleted = 0;
        publishDate = new Date();
    }

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

    public Integer isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Integer deleted) {
        isDeleted = deleted;
    }

    //endregion
}
