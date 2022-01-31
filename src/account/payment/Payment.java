package account.payment;

import account.user.User;

import javax.persistence.*;

@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String period;
    private long salary;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Payment(String period, long salary, User user) {
        this.period = period;
        this.salary = salary;
        this.user = user;
    }

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}