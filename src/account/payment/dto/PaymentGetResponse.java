package account.payment.dto;

import account.payment.Payment;
import account.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class PaymentGetResponse {

    private final String name;
    private final String lastname;
    private final String period;
    private final String salary;

    @JsonIgnore
    private YearMonth yearMonth;

    public PaymentGetResponse(Payment payment, User user) {
        name = user.getName();
        lastname = user.getLastname();
        salary = payment.getSalary() / 100 + " dollar(s) "
                + payment.getSalary() % 100 + " cent(s)";
        yearMonth = YearMonth.parse(
                payment.getPeriod(),
                DateTimeFormatter.ofPattern("MM-yyyy")
        );
        period = String.format("%s-%d",
                yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                yearMonth.getYear()
        );
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPeriod() {
        return period;
    }

    public String getSalary() {
        return salary;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

}