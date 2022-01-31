package account.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    boolean existsByUserIdAndPeriod(long userId, String period);

    Optional<Payment> findByUserIdAndPeriod(long userId, String period);

    List<Payment> findAllByUserId(long userId);

}