package by.karelin.business.dto.Requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank(message = "Password must not be blank or empty.")
    @Size(min = 6, message = "Password must be between 6 and 20 characters")
    private String oldPassword;
    @NotBlank(message = "Password must not be blank or empty.")
    @Size(min = 6, message = "Password must be between 6 and 20 characters")
    private String newPassword;

    public ChangePasswordRequest() {}

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
