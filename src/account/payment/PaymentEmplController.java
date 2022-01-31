package account.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/empl/")
public class PaymentEmplController {

    private final PaymentEmplService employeeService;

    @Autowired
    public PaymentEmplController(PaymentEmplService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("payment")
    public ResponseEntity<Object> getPayment(@RequestParam(required = false) String period) {
        return ResponseEntity.ok(employeeService.get(period));
    }

}