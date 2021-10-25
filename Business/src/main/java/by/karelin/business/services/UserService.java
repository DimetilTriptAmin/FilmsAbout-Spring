package by.karelin.business.services;

import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.dto.Responses.UpdateResponse;
import by.karelin.business.dto.Responses.UserResponse;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.IUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ServiceResponse<UserResponse> GetUser(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            UserResponse userResponse = new UserResponse(user.getName(), user.getEmail(), user.getAvatar());
            return new ServiceResponse<UserResponse>(userResponse);
        }

        return new ServiceResponse<>("User not found");
    }

    public ServiceResponse<UpdateResponse> UpdateAsync(Long userId, UpdateRequest updateRequest) {
        if (!userRepository.existsById(userId)) {
            return new ServiceResponse<>("User not found");
        }
        User user = userRepository.getById(userId);

        user.setAvatar(updateRequest.getAvatar());
        userRepository.save(user);

        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setAvatar(user.getAvatar());

        return new ServiceResponse<>(updateResponse);
    }

    /* TODO Implement tokens
    public ServiceResponse<LoginResponse> LoginUser(LoginRequest loginRequest)
    {
        try
        {
            var user = await _userManager.FindByNameAsync(loginRequest.Username);

            if (user == null)
            {
                return new GenericResponse<LoginResponse>("No such username.");
            }

            var isPasswordCorrect = await _userManager.CheckPasswordAsync(user, loginRequest.Password);

            if (!isPasswordCorrect)
            {
                return new GenericResponse<LoginResponse>("Password does not match.");
            }

            LoginResponse response = AuthorizeUser(user);

            user.refreshToken = response.RefreshToken;
            await _userManager.UpdateAsync(user);

            return new GenericResponse<LoginResponse>(response);
        }
        catch
        {
            return new GenericResponse<LoginResponse>("Server is offline.");
        }
    }

    public async Task<GenericResponse<bool>> LogoutAsync(int id)
    {
        try
        {
            User user = await _unitOfWork.UserRepository.GetAsync(id);

            if (user == null)
            {
                return new GenericResponse<bool>("Invalid token.");
            }

            user.refreshToken = "";
            await _unitOfWork.UserRepository.UpdateAsync(user);
            await _unitOfWork.SaveAsync();
            return new GenericResponse<bool>(true);
        }
        catch
        {
            return new GenericResponse<bool>("Server is offline.");
        }
    }

    public async Task<GenericResponse<LoginResponse>> RefreshAsync(string token)
    {
        try
        {
            var request = await _unitOfWork.UserRepository.Filter(user => user.refreshToken == token);

            var user = request.FirstOrDefault();

            if (user == null)
            {
                return new GenericResponse<LoginResponse>("Invalid token.");
            }

            LoginResponse response = AuthorizeUser(user);
            user.refreshToken = response.RefreshToken;
            await _unitOfWork.UserRepository.UpdateAsync(user);
            await _unitOfWork.SaveAsync();
            return new GenericResponse<LoginResponse>(response);
        }
        catch
        {
            return new GenericResponse<LoginResponse>("Server is offline.");
        }
    }

    public async Task<GenericResponse<LoginResponse>> RegisterUserAsync(RegisterRequest registerRequest)
    {
        try
        {
            var user = await _userManager.FindByNameAsync(registerRequest.Username);
            if (user != null)
            {
                return new GenericResponse<LoginResponse>("This username is taken.");
            }

            user = await _userManager.FindByEmailAsync(registerRequest.Email);
            if (user != null)
            {
                return new GenericResponse<LoginResponse>("This email is registered.");

            }

            if (registerRequest.Password != registerRequest.ConfirmPassword)
            {
                return new GenericResponse<LoginResponse>("Passwords must be equal.");
            }

            user = new User()
            {
                UserName = registerRequest.Username,
                Email = registerRequest.Email,
                Avatar = Base64Coder.EncodeImg(Path.GetFullPath(@"../FilmsAboutBack/Assets/Img/default-avatar.jpg")),
            };

            var result = await _userManager.CreateAsync(user, registerRequest.Password);
            LoginResponse response = AuthorizeUser(user);
            user.refreshToken = response.RefreshToken;
            await _userManager.UpdateAsync(user);

            if (!result.Succeeded)
            {
                return new GenericResponse<LoginResponse>(string.Join(",", result.Errors.Select(e => e.Description)));
            }

            return new GenericResponse<LoginResponse>(response);
        }
        catch
        {
            return new GenericResponse<LoginResponse>("Server is offline.");
        }
    }

    public async Task<GenericResponse<bool>> ChangePasswordAsync(int id, ChangePasswordRequest changePasswordRequest)
    {
        try
        {
            var user = await _userManager.FindByIdAsync(id.ToString());

            if (user == null)
            {
                return new GenericResponse<bool>("Bad request.");
            }

            var isPasswordCorrect = await _userManager.CheckPasswordAsync(user, changePasswordRequest.OldPassword);

            if (!isPasswordCorrect)
            {
                return new GenericResponse<bool>("Password incorrect..");
            }

            user.PasswordHash = _userManager.PasswordHasher.HashPassword(user, changePasswordRequest.NewPassword);
            await _userManager.UpdateAsync(user);
            return new GenericResponse<bool>(true);
        }
        catch
        {
            return new GenericResponse<bool>("Server is offline.");
        }
    }

    private LoginResponse AuthorizeUser(User user)
    {
        var accessToken = _generator.GenerateAccessToken(user);
        var refreshToken = _generator.GenerateRefreshToken();

        return new LoginResponse(accessToken, refreshToken);
    }
     */
}
