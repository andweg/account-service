package account.payment;

import account.exception.PaymentAlreadyExists;
import account.exception.PaymentNotFound;
import account.exception.UserDoesNotExist;
import account.payment.dto.PaymentPostRequest;
import account.payment.dto.PaymentResponse;
import account.user.User;
import account.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentAcctService {

    private final UserRepository userRepo;
    private final PaymentRepository paymentRepo;

    @Autowired
    public PaymentAcctService(UserRepository userRepo,
                              PaymentRepository paymentRepo) {
        this.userRepo = userRepo;
        this.paymentRepo = paymentRepo;
    }

    public PaymentResponse add(List<PaymentPostRequest> inputPayments) {
        for (PaymentPostRequest inputPayment : inputPayments) {
            validateNew(inputPayment.getEmployee(), inputPayment.getPeriod());
            Payment payment = create(inputPayment);
            paymentRepo.save(payment);
        }
        return new PaymentResponse("Added successfully!");
    }

    public PaymentResponse update(PaymentPostRequest inputPayment) {
        Payment payment = find(
                inputPayment.getEmployee(),
                inputPayment.getPeriod()
        );
        payment.setSalary(inputPayment.getSalary());
        paymentRepo.save(payment);
        return new PaymentResponse("Updated successfully!");
    }

    private void validateNew(String employeeEmail, String period) {
        long userId = getUserOrThrow(employeeEmail).getId();
        throwIfExists(userId, period);
    }

    private void throwIfExists(long userId, String period) {
        if (paymentRepo.existsByUserIdAndPeriod(userId, period)) {
            throw new PaymentAlreadyExists();
        }
    }

    private Payment create(PaymentPostRequest inputPayment) {
        return new Payment(
                inputPayment.getPeriod(),
                inputPayment.getSalary(),
                getUserOrThrow(inputPayment.getEmployee())
        );
    }

    private Payment find(String employee, String period) {
        long userId = getUserOrThrow(employee).getId();
        return paymentRepo
                .findByUserIdAndPeriod(userId, period)
                .orElseThrow(PaymentNotFound::new);
    }

    private User getUserOrThrow(String email) {
        return userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(UserDoesNotExist::new);
    }

}