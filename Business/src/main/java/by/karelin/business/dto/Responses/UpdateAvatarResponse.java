package by.karelin.business.dto.Responses;

public class UpdateAvatarResponse {
    private String avatar;

    public UpdateAvatarResponse() {}
    public UpdateAvatarResponse(String avatar) {this.avatar = avatar;}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
