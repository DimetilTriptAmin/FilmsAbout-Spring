package by.karelin.business.services.interfaces;

import by.karelin.persistence.dto.Requests.LoginRequest;
import by.karelin.persistence.dto.Requests.RegisterRequest;
import by.karelin.persistence.dto.Requests.UpdateRequest;
import by.karelin.persistence.dto.Responses.LoginResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.persistence.dto.Responses.UpdateResponse;
import by.karelin.persistence.dto.Responses.UserResponse;

public interface IUserService {
    ServiceResponse<UserResponse> getById(Long userId);
    ServiceResponse<UpdateResponse> updateAvatar(Long userId, UpdateRequest updateRequest);
    ServiceResponse<LoginResponse> loginUser(LoginRequest loginData);
    ServiceResponse<LoginResponse> registerUser(RegisterRequest loginData);
}
