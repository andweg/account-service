package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "New password must be differ from the previous one!"
)
public class PasswordUnchanged extends RuntimeException { }