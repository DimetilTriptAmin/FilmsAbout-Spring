package by.karelin.webapi.controllers;

import by.karelin.business.dto.Requests.UpdateRequest;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.dto.Responses.UpdateResponse;
import by.karelin.business.dto.Responses.UserResponse;
import by.karelin.business.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/User")
public class UserController {
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity GetUserAsync() {
        //TODO token
        //var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];
        //int userId = _tokenDecoder.getUserIdFromToken(token);
        Long userId = Long.MAX_VALUE;

        ServiceResponse<UserResponse> response = userService.GetUser(userId);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity Update(@RequestBody UpdateRequest updateData) {
        //TODO token
        //var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];
        //int userId = _tokenDecoder.getUserIdFromToken(token);
        Long userId = Long.MAX_VALUE;

        ServiceResponse<UpdateResponse> response = userService.UpdateAsync(userId, updateData);

        if (!response.IsSucceeded()) {
            return new ResponseEntity(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response.getValue(), HttpStatus.OK);
    }
    /* TODO implement security

        [HttpPost("login")]
    public async Task<IActionResult> LoginAsync([FromBody] LoginRequest loginData)
    {
        var response = await _userService.LoginUserAsync(loginData);

        if (!response.IsSucceeded) return BadRequest(response.ErrorMessage);
        else
        {
            SetCookie(response.Value);
            return Ok(response.Value);
        }
    }

        [HttpPost("register")]
    public async Task<IActionResult> RegisterAsync([FromBody] RegisterRequest registerData)
    {
        var response = await _userService.RegisterUserAsync(registerData);

        if (!response.IsSucceeded) return BadRequest(response.ErrorMessage);
        else
        {
            SetCookie(response.Value);
            return Ok(response.Value);
        }
    }

        [HttpPut("refresh")]
    public async Task<IActionResult> RefreshAsync()
    {
        Request.Cookies.TryGetValue("refreshToken", out string refreshToken);

        bool ValidationResult = _refreshTokenValidator.Validate(refreshToken);
        if (!ValidationResult) return Unauthorized("Invalid token.");

        var response = await _userService.RefreshAsync(refreshToken);


        if (!response.IsSucceeded) return BadRequest(response.ErrorMessage);
        else
        {
            SetCookie(response.Value);
            return Ok(response.Value);
        }
    }

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

        [HttpDelete("logout")]
            [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    public async Task<IActionResult> LogoutAsync()
    {
        var token = Request.Headers["Authorization"].ToString().Split()[Constants.TOKEN_VALUE_INDEX];

        int userId = _tokenDecoder.getUserIdFromToken(token);

        var response = await _userService.LogoutAsync(userId);

        SetCookie(new LoginResponse("", ""));

        if (!response.IsSucceeded) return BadRequest(response.ErrorMessage);
        return Ok(response.Value);
    }

    private void SetCookie(LoginResponse response)
    {
        var cookieOptions = new CookieOptions
        {
            HttpOnly = true,
                    Expires = DateTimeOffset.Now.AddMinutes(Constants.MINUTES_IN_MONTH),
                    SameSite = SameSiteMode.None,
                    Secure = true,
        };
        Response.Cookies.Append("refreshToken", response.RefreshToken, cookieOptions);
    }
     */
}
