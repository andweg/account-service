package account.payment;

import account.exception.PaymentNotFound;
import account.payment.dto.PaymentGetResponse;
import account.user.User;
import account.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentEmplService {

    private final UserRepository userRepo;
    private final PaymentRepository paymentRepo;

    @Autowired
    public PaymentEmplService(UserRepository userRepo,
                              PaymentRepository paymentRepo) {
        this.userRepo = userRepo;
        this.paymentRepo = paymentRepo;
    }

    public Object get(String period) {
        User user = userRepo
                .findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();
        if (period == null) {
            return getAll(user);
        } else {
            return getSingle(user, period);
        }
    }

    private PaymentGetResponse getSingle(User user, String period) {
        return new PaymentGetResponse(
                find(user.getId(), period),
                userRepo.findByEmailIgnoreCase(user.getEmail()).get()
        );
    }

    private List<PaymentGetResponse> getAll(User user) {
        List<Payment> payments = paymentRepo.findAllByUserId(user.getId());
        List<PaymentGetResponse> output = new ArrayList<>();
        for (Payment payment : payments) {
            output.add(new PaymentGetResponse(
                    payment,
                    userRepo.findByEmailIgnoreCase(user.getEmail()).get()
            ));
        }
        output.sort(new PaymentComparator());
        return output;
    }

    private Payment find(long userId, String period) {
        return paymentRepo
                .findByUserIdAndPeriod(userId, period)
                .orElseThrow(PaymentNotFound::new);
    }

}