package by.karelin.domain.models;

import javax.persistence.*;

@Entity
@Table(name="FA_RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    private Film film;

    //region ctors
    public Rating(){}
    public Rating(int rate, Film film, User user){
        this.rate = rate;
        this.film = film;
        this.user = user;
    }
    //endregion

    //region getters&setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    //endregion
}
