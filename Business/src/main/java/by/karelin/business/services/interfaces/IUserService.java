package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.*;

public interface IUserService {
    ServiceResponse<UserResponse> getById(Long userId);
    ServiceResponse<UpdateAvatarResponse> updateAvatar(Long userId, UpdateRequest updateRequest);
    ServiceResponse<LoginResponse> loginUser(LoginRequest loginData);
    ServiceResponse<LoginResponse> registerUser(RegisterRequest loginData);
    ServiceResponse<LoginResponse> changePassword(Long userId,ChangePasswordRequest passwordRequest);
}
