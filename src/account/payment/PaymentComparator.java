package account.payment;

import account.payment.dto.PaymentGetResponse;

import java.util.Comparator;

public class PaymentComparator implements Comparator<PaymentGetResponse> {

    @Override
    public int compare(PaymentGetResponse p1, PaymentGetResponse p2) {
        if (p1.getYearMonth().isAfter(p2.getYearMonth())) {
            return -1;
        } else if (p2.getYearMonth().isAfter(p1.getYearMonth())) {
            return 1;
        } else {
            return 0;
        }
    }

}