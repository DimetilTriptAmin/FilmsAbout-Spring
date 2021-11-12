package by.karelin.business.dto.Responses;

public class LoginResponse {
    private String accessToken;

    public LoginResponse(String token){
        accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
