package account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.BAD_REQUEST,
        reason = "The submitted payment already exists in the database!"
)
public class PaymentAlreadyExists extends RuntimeException { }