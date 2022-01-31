package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "Unsupported operation!"
)
public class UnsupportedOperation extends RuntimeException {

}