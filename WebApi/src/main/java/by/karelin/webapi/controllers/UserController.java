package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.*;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.business.utils.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/User")
public class UserController {
    private final IUserService userService;
    private final JwtProvider jwtProvider;

    public UserController(IUserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public ResponseEntity getUser(@RequestHeader("Authorization") String rawToken) {
        String token = rawToken.substring("Bearer ".length());
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<UserResponse> response = userService.getById(userId);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PutMapping(value = "/avatar")
    public ResponseEntity update(
            @RequestHeader("Authorization") String rawToken,
            @RequestBody UpdateRequest updateData
    ) {
        String token = rawToken.substring("Bearer ".length());
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<UpdateAvatarResponse> response = userService.updateAvatar(userId, updateData);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody LoginRequest loginData) {
        ServiceResponse<LoginResponse> response = userService.loginUser(loginData);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response.getValue(), HttpStatus.OK);

    }

    @PostMapping(value = "register")
    public ResponseEntity register(@RequestBody RegisterRequest registerData)
    {
        ServiceResponse<LoginResponse> response = userService.registerUser(registerData);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PutMapping("password")
    public ResponseEntity changePassword(
            @RequestHeader("Authorization") String rawToken,
            @RequestBody ChangePasswordRequest changePasswordRequest)
    {
        String token = rawToken.substring("Bearer ".length());
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<ChangePasswordResponse> response =
                userService.changePassword(userId, changePasswordRequest);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

}
