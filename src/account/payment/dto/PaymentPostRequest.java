package account.payment.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PaymentPostRequest {

    @NotBlank
    private String period;

    @NotBlank
    @Range(min=0)
    private long salary;

    @NotBlank
    @Pattern(regexp="[\\w.]+@acme.com")
    private String employee;

    public PaymentPostRequest(String period, long salary, String employee) {
        this.period = period;
        this.salary = salary;
        this.employee = employee;
    }

    public PaymentPostRequest() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

}