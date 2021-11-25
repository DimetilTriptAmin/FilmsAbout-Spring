package by.karelin.business.dto.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateCommentRequest {
    @NotNull(message = "Comment ID must be specified")
    private Long commentId;
    @NotBlank(message = "Comment text must not be blank or empty")
    @Size(min = 1, message = "Comment text be between 1 and 255 characters")
    private String text;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
