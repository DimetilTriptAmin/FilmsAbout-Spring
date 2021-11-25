package by.karelin.business.validators;

import by.karelin.business.dto.Requests.LoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class LoginRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return LoginRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        LoginRequest req = (LoginRequest) obj;
        if (req.getPassword().isEmpty() || req.getPassword().isBlank()) {
            errors.rejectValue("password", "value.empty");
        }

        if (req.getPassword().length() < 2 || req.getPassword().length() > 20) {
            errors.rejectValue("password", "value.invalidSize");
        }

        if (req.getUsername().isEmpty() || req.getUsername().isBlank()) {
            errors.rejectValue("username", "value.empty");
        }

        if (req.getPassword().length() < 2 || req.getPassword().length() > 20) {
            errors.rejectValue("username", "value.invalidSize");
        }
    }
}
