package by.karelin.webapi.controllers;

import by.karelin.persistence.dto.Requests.LoginRequest;
import by.karelin.persistence.dto.Requests.RegisterRequest;
import by.karelin.persistence.dto.Requests.UpdateRequest;
import by.karelin.persistence.dto.Responses.LoginResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.persistence.dto.Responses.UpdateResponse;
import by.karelin.persistence.dto.Responses.UserResponse;
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

        if (!response.IsSucceeded()) {
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

        ServiceResponse<UpdateResponse> response = userService.updateAvatar(userId, updateData);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody LoginRequest loginData) {
        ServiceResponse<LoginResponse> response = userService.loginUser(loginData);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }

        //SetCookie(response.Value);
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

        //SetCookie(response.Value);
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }
    /*
        [HttpPut("changePassword")]
            [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    public async Task<IActionResult> ChangePasswordAsync([FromBody] ChangePasswordRequest changePasswordRequest)
    {
        var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];

        int userId = _tokenDecoder.getUserIdFromToken(token);

        var response = await _userService.ChangePasswordAsync(userId, changePasswordRequest);

        if (!response.IsSucceeded) return BadRequest(response.ErrorMessage);
        return Ok(response.Value);
    }
     */
}
