package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.ChangePasswordRequest;
import by.karelin.business.dto.Requests.LoginRequest;
import by.karelin.business.dto.Requests.RegisterRequest;
import by.karelin.business.dto.Requests.UpdateAvatarRequest;
import by.karelin.business.dto.Responses.*;
import by.karelin.business.services.interfaces.IUserService;
import by.karelin.business.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/User")
public class UserController {
    private final IUserService userService;
    private final JwtProvider jwtProvider;

    /*
    @Autowired
    @Qualifier("loginRequestValidator") // spring validator
    private Validator loginRequestValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(loginRequestValidator);
    }*/

    public UserController(IUserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public ResponseEntity getUser(@RequestHeader("Authorization") String rawToken) {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
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
            @Valid @RequestBody UpdateAvatarRequest updateData
    ) {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<UpdateAvatarResponse> response = userService.updateAvatar(userId, updateData);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginData/*, BindingResult bindingResult*/) {

        /*
        if (bindingResult.hasErrors()) {
            return new ResponseEntity("Model invalid", HttpStatus.BAD_REQUEST);
        }*/

        ServiceResponse<LoginResponse> response = userService.loginUser(loginData);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response.getValue(), HttpStatus.OK);

    }

    @PostMapping(value = "register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerData)
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
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest)
    {
        String token = rawToken.substring("Bearer ".length());
        boolean Unauthorized =  !jwtProvider.validateToken(token);
        if(Unauthorized){
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Long userId = jwtProvider.getIdFromToken(token);

        ServiceResponse<LoginResponse> response =
                userService.changePassword(userId, changePasswordRequest);

        if (!response.IsSucceeded())
        {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

}
