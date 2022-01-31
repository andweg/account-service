package account.payment;

import account.payment.dto.PaymentPostRequest;
import account.payment.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/acct/")
public class PaymentAcctController {

    private final PaymentAcctService accountantService;

    @Autowired
    public PaymentAcctController(PaymentAcctService accountantService) {
        this.accountantService = accountantService;
    }

    @PostMapping("payments")
    public PaymentResponse postPayments(@RequestBody List<PaymentPostRequest> payments) {
        return accountantService.add(payments);
    }

    @PutMapping("payments")
    public PaymentResponse putPayments(@RequestBody PaymentPostRequest payment) {
        return accountantService.update(payment);
    }

}