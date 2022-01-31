package account.user.dto;

import account.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class SignupResponse {

    private final long id;
    private final String name;
    private final String lastname;
    private final String email;
    private final List<String> roles;

    public SignupResponse(long id, String name, String lastname, String email, List<String> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }

    public SignupResponse(User user) {
        id = user.getId();
        name = user.getName();
        lastname = user.getLastname();
        email = user.getEmail();
        roles = user.getRolesAsString().stream()
                .sorted().collect(Collectors.toList());
    }

    public long getId() {
        return id;
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

    public List<String> getRoles() {
        return roles;
    }

}