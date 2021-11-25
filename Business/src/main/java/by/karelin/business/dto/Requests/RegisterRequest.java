package by.karelin.business.dto.Requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Username must not be blank or empty")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Password must not be blank or empty")
    @Size(min = 6, message = "Password must be between 6 and 20 characters")
    private String password;
    @NotBlank(message = "Password must not be blank or empty")
    @Size(min = 6, message = "Password must be between 6 and 20 characters")
    private String confirmPassword;

    //#region getters&setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    //#endregion
}
