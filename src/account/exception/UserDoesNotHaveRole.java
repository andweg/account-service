package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "The user does not have the role specified!"
)
public class UserDoesNotHaveRole extends RuntimeException {
}