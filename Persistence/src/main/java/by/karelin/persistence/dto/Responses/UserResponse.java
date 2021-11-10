package by.karelin.persistence.dto.Responses;

public class UserResponse {
    private String userName;
    private String email;
    private String avatar;

    //region ctors
    public UserResponse(){}

    public UserResponse(String userName, String email, String avatar){
        this.userName = userName;
        this.email = email;
        this.avatar = avatar;
    }
    //endregion

    //region getters&setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //endregion
}
