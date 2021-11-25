package by.karelin.business.dto.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateCommentRequest {
    @NotNull(message = "Film ID must be specified")
    private Long filmId;
    @NotBlank(message = "Comment text must not be blank or empty")
    @Size(min = 1, message = "Comment text be between 1 and 255 characters")
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
