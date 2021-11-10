package by.karelin.business.services;

import by.karelin.persistence.dto.Requests.LoginRequest;
import by.karelin.persistence.dto.Requests.RegisterRequest;
import by.karelin.persistence.dto.Requests.UpdateRequest;
import by.karelin.persistence.dto.Responses.LoginResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.persistence.dto.Responses.UpdateResponse;
import by.karelin.persistence.dto.Responses.UserResponse;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.business.utils.Base64Coder;
import by.karelin.business.utils.JwtProvider;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserService(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public ServiceResponse<UserResponse> getById(Long userId) {
        User user = userRepository.getById(userId);

        if (user == null) {
            return new ServiceResponse<UserResponse>("User not found");
        }

        UserResponse response = modelMapper.map(user, UserResponse.class);

        return new ServiceResponse<UserResponse>(response);
    }

    public ServiceResponse<UpdateResponse> updateAvatar(Long userId, UpdateRequest updateRequest) {
        String newAvatar = updateRequest.getAvatar();
        User user = userRepository.getById(userId);

        boolean updateFailed = !userRepository.tryUpdateAvatar(user.getId(), newAvatar);

        if (updateFailed) {
            return new ServiceResponse<UpdateResponse>("Could not update the avatar");
        }

        UpdateResponse updateResponse = new UpdateResponse(newAvatar);

        return new ServiceResponse<>(updateResponse);
    }

    public ServiceResponse<LoginResponse> loginUser(LoginRequest loginRequest)
    {
        try
        {
            User user = userRepository.getByUsername(loginRequest.getUsername());

            if (user == null)
            {
                return new ServiceResponse<LoginResponse>("No such Username.");
            }

            boolean isPasswordCorrect = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());

            if (!isPasswordCorrect)
            {
                return new ServiceResponse<LoginResponse>("Password does not match.");
            }

            LoginResponse response = AuthorizeUser(user);

            return new ServiceResponse<LoginResponse>(response);
        }
        catch(Exception ex)
        {
            return new ServiceResponse<LoginResponse>("Server is offline.");
        }
    }

    public ServiceResponse<LoginResponse> registerUser(RegisterRequest registerRequest)
    {
        try
        {
            User user = userRepository.getByUsername(registerRequest.getUsername());

            if (user != null)
            {
                return new ServiceResponse<LoginResponse>("This Username is taken.");
            }

            user = userRepository.getByEmail(registerRequest.getEmail());
            if (user != null)
            {
                return new ServiceResponse<LoginResponse>("This email is registered.");
            }

            if (!registerRequest.getPassword().contentEquals(registerRequest.getConfirmPassword()))
            {
                return new ServiceResponse<LoginResponse>("Passwords must be equal.");
            }

            user = new User(
                    registerRequest.getUsername(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    Base64Coder.EncodeImg("D:\\University\\Course\\FilmsAbout\\Business\\assets\\default-avatar.png"),
                    registerRequest.getEmail()
            );

            boolean succeeded = userRepository.tryRegisterUser(user);

            if(!succeeded)
            {
                return new ServiceResponse<LoginResponse>("Register failed.");
            }

            LoginResponse response = AuthorizeUser(user);

            return new ServiceResponse<LoginResponse>(response);
        }
        catch(Exception ex)
        {
            return new ServiceResponse<LoginResponse>("Server is offline.");
        }
    }
    /*

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
     */
    private LoginResponse AuthorizeUser(User user)
    {
        String accessToken = jwtProvider.generateToken(user.getId());
        //var refreshToken = _generator.GenerateRefreshToken();

        return new LoginResponse(accessToken);
    }
}
