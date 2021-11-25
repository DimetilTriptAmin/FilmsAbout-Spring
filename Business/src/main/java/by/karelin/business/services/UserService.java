package by.karelin.business.services;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateAvatarRequest;
import by.karelin.business.dto.Responses.*;
import by.karelin.business.services.interfaces.IMailService;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.business.utils.Base64Coder;
import by.karelin.business.utils.enums.ErrorCode;
import by.karelin.business.utils.JwtProvider;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final IMailService mailService;
    private static Logger log = Logger.getLogger(RatingService.class.getName());

    public UserService(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, IMailService mailService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.mailService = mailService;
    }

    @Override
    public ServiceResponse<List<String>> getEmails() {
        try {
            List<String> emails = userRepository.getAllEmails();

            if (emails == null) {
                return new ServiceResponse<>("Users not found", HttpStatus.NOT_FOUND);
            }

            return new ServiceResponse<>(emails, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<UserResponse> getById(Long userId) {
        try {
            User user = userRepository.getById(userId);

            if (user == null) {
                return new ServiceResponse<>("User not found", HttpStatus.NOT_FOUND);
            }

            UserResponse response = modelMapper.map(user, UserResponse.class);

            return new ServiceResponse<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<UpdateAvatarResponse> updateAvatar(Long userId, UpdateAvatarRequest updateRequest) {
        try {
            String newAvatar = updateRequest.getAvatar();
            User user = userRepository.getById(userId);

            Long idResult = userRepository.updateAvatar(user.getId(), newAvatar);

            if (idResult == ErrorCode.RepositoryTransactionError.getValue()) {
                return new ServiceResponse<>("Could not update the avatar", HttpStatus.BAD_REQUEST);
            }

            UpdateAvatarResponse updateResponse = new UpdateAvatarResponse(newAvatar);

            return new ServiceResponse<>(updateResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<LoginResponse> loginUser(LoginRequest loginRequest) {
        try {
            User user = userRepository.getByUsername(loginRequest.getUsername());

            if (user == null) {
                return new ServiceResponse<>("No such Username.", HttpStatus.BAD_REQUEST);
            }

            boolean isPasswordCorrect = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());

            if (!isPasswordCorrect) {
                return new ServiceResponse<>("Password does not match.", HttpStatus.BAD_REQUEST);
            }

            LoginResponse response = AuthorizeUser(user);

            return new ServiceResponse<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<LoginResponse> registerUser(RegisterRequest registerRequest) {
        try {
            User user = userRepository.getByUsername(registerRequest.getUsername());

            if (user != null) {
                return new ServiceResponse<>("This Username is taken.", HttpStatus.BAD_REQUEST);
            }

            user = userRepository.getByEmail(registerRequest.getEmail());
            if (user != null) {
                return new ServiceResponse<>("This email is registered.", HttpStatus.BAD_REQUEST);
            }

            if (!registerRequest.getPassword().contentEquals(registerRequest.getConfirmPassword())) {
                return new ServiceResponse<>("Passwords must be equal.", HttpStatus.BAD_REQUEST);
            }

            user = new User(
                    registerRequest.getUsername(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    Base64Coder.EncodeImg("D:\\University\\Course\\FilmsAbout\\Business\\assets\\default-avatar.png"),
                    registerRequest.getEmail()
            );

            Long userId = userRepository.registerUser(user);

            if (userId == ErrorCode.RepositoryTransactionError.getValue()) {
                return new ServiceResponse<>("Register failed.", HttpStatus.BAD_REQUEST);
            }

            ServiceResponse<List<String>> mailResponse = getEmails();

            if(mailResponse.IsSucceeded()){
                mailService.sendAll(
                        mailResponse.getValue(), "newcomers", "User " + user.getName() + " have just registered"
                );
            }

            LoginResponse response = AuthorizeUser(userId);

            return new ServiceResponse<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ServiceResponse<LoginResponse> changePassword(Long userId, ChangePasswordRequest passwordRequest) {
        try {
            User user = userRepository.getById(userId);

            if (user == null) {
                return new ServiceResponse<>("No such User.", HttpStatus.BAD_REQUEST);
            }

            boolean isPasswordCorrect = passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPasswordHash());

            if (!isPasswordCorrect) {
                return new ServiceResponse<>("Password does not match.", HttpStatus.BAD_REQUEST);
            }

            String newPasswordHash = passwordEncoder.encode(passwordRequest.getNewPassword());

            Long resultId = userRepository.changePassword(userId, newPasswordHash);

            if (resultId == ErrorCode.RepositoryTransactionError.getValue()) {
                return new ServiceResponse<>("Could not change the password", HttpStatus.BAD_REQUEST);
            }

            return new ServiceResponse<>(AuthorizeUser(resultId), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Server is offline.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private LoginResponse AuthorizeUser(User user) {
        String accessToken = jwtProvider.generateToken(user.getId());
        return new LoginResponse(accessToken);
    }

    private LoginResponse AuthorizeUser(Long id) {
        String accessToken = jwtProvider.generateToken(id);
        return new LoginResponse(accessToken);
    }
}
