package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "Password length cannot be shorter than 12 characters!"
)
public class PasswordTooShort extends RuntimeException { }