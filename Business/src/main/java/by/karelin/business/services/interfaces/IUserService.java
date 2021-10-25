package by.karelin.business.services.interfaces;

import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.dto.Responses.UpdateResponse;
import by.karelin.business.dto.Responses.UserResponse;

public interface IUserService {
    ServiceResponse<UserResponse> GetUser(Long id);
    ServiceResponse<UpdateResponse> UpdateAsync(Long userId, UpdateRequest updateRequest);
}
