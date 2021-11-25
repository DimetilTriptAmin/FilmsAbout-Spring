package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateAvatarRequest;
import by.karelin.business.dto.Responses.*;

import java.util.List;

public interface IUserService {
    ServiceResponse<List<String>> getEmails();
    ServiceResponse<UserResponse> getById(Long userId);
    ServiceResponse<UpdateAvatarResponse> updateAvatar(Long userId, UpdateAvatarRequest updateRequest);
    ServiceResponse<LoginResponse> loginUser(LoginRequest loginData);
    ServiceResponse<LoginResponse> registerUser(RegisterRequest loginData);
    ServiceResponse<LoginResponse> changePassword(Long userId,ChangePasswordRequest passwordRequest);
}
