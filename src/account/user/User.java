package account.user;

import account.payment.Payment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean accountNonLocked;
    private int failedLoginAttempts;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    private Set<Payment> payments = new HashSet<>();

    @ElementCollection(
            fetch = FetchType.EAGER
    )
    private Set<UserRole> roles = new HashSet<>();

    public void removeRole(UserRole role) {
        this.roles.remove(role);
    }
    public void addRole(UserRole role) {
        this.roles.add(role);
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public Set<String> getRolesAsString() {
        return roles.stream().map(Enum::name).collect(Collectors.toSet());
    }

    public boolean hasAdminRole() {
        return roles.contains(UserRole.ROLE_ADMINISTRATOR);
    }

    public User(String name,
                String lastname,
                String email,
                String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(Long id,
                String name,
                String lastname,
                String email,
                String password,
                boolean accountNonLocked,
                int failedLoginAttempts,
                Set<Payment> payments,
                Set<UserRole> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.failedLoginAttempts = failedLoginAttempts;
        this.payments = payments;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

}