package by.karelin.persistence.dto.Requests;

public class CreateCommentRequest {
    private Long filmId;
    private String text;

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
