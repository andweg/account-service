package account.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SignupRequest {

    @NotBlank
    private final String name;

    @NotBlank
    private final String lastname;

    @Email
    @NotNull
    @Pattern(regexp="[\\w.]+@acme.com")
    private final String email;

    @NotBlank
    private final String password;

    public SignupRequest(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}