package by.karelin.business.services;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.*;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.business.utils.Base64Coder;
import by.karelin.business.utils.ErrorCode;
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
            return new ServiceResponse<>("User not found");
        }

        UserResponse response = modelMapper.map(user, UserResponse.class);

        return new ServiceResponse<>(response);
    }

    public ServiceResponse<UpdateAvatarResponse> updateAvatar(Long userId, UpdateRequest updateRequest) {
        String newAvatar = updateRequest.getAvatar();
        User user = userRepository.getById(userId);

        Long idResult = userRepository.updateAvatar(user.getId(), newAvatar);

        if (idResult == ErrorCode.RepositoryTransactionError.getValue()) {
            return new ServiceResponse<>("Could not update the avatar");
        }

        UpdateAvatarResponse updateResponse = new UpdateAvatarResponse(newAvatar);

        return new ServiceResponse<>(updateResponse);
    }

    public ServiceResponse<LoginResponse> loginUser(LoginRequest loginRequest)
    {
        try
        {
            User user = userRepository.getByUsername(loginRequest.getUsername());

            if (user == null)
            {
                return new ServiceResponse<>("No such Username.");
            }

            boolean isPasswordCorrect = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());

            if (!isPasswordCorrect)
            {
                return new ServiceResponse<>("Password does not match.");
            }

            LoginResponse response = AuthorizeUser(user);

            return new ServiceResponse<>(response);
        }
        catch(Exception ex)
        {
            return new ServiceResponse<>("Server is offline.");
        }
    }

    public ServiceResponse<LoginResponse> registerUser(RegisterRequest registerRequest)
    {
        try
        {
            User user = userRepository.getByUsername(registerRequest.getUsername());

            if (user != null)
            {
                return new ServiceResponse<>("This Username is taken.");
            }

            user = userRepository.getByEmail(registerRequest.getEmail());
            if (user != null)
            {
                return new ServiceResponse<>("This email is registered.");
            }

            if (!registerRequest.getPassword().contentEquals(registerRequest.getConfirmPassword()))
            {
                return new ServiceResponse<>("Passwords must be equal.");
            }

            user = new User(
                    registerRequest.getUsername(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    Base64Coder.EncodeImg("D:\\University\\Course\\FilmsAbout\\Business\\assets\\default-avatar.png"),
                    registerRequest.getEmail()
            );

            Long userId = userRepository.registerUser(user);

            if(userId == ErrorCode.RepositoryTransactionError.getValue())
            {
                return new ServiceResponse<>("Register failed.");
            }

            LoginResponse response = AuthorizeUser(user);

            return new ServiceResponse<>(response);
        }
        catch(Exception ex)
        {
            return new ServiceResponse<>("Server is offline.");
        }
    }

    @Override
    public ServiceResponse<LoginResponse> changePassword(Long userId, ChangePasswordRequest passwordRequest) {
        Long resultId = userRepository.changePassword(userId, passwordRequest.getNewPassword());

        if (resultId == ErrorCode.RepositoryTransactionError.getValue())
        {
            return new ServiceResponse<>("Could not change the password");
        }

        return new ServiceResponse<>(AuthorizeUser(resultId));
    }

    private LoginResponse AuthorizeUser(User user)
    {
        String accessToken = jwtProvider.generateToken(user.getId());
        return new LoginResponse(accessToken);
    }

    private LoginResponse AuthorizeUser(Long id)
    {
        String accessToken = jwtProvider.generateToken(id);
        return new LoginResponse(accessToken);
    }
}
