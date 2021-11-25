package by.karelin.business.dto.Requests;

import javax.validation.constraints.NotBlank;

public class UpdateAvatarRequest {
    @NotBlank(message = "Avatar must not be blank or empty")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
