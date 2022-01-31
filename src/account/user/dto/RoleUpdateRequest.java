package account.user.dto;

import javax.validation.constraints.NotBlank;

public class RoleUpdateRequest {

    @NotBlank
    private final String user;

    @NotBlank
    private final String role;

    @NotBlank
    private final String operation;

    public RoleUpdateRequest(String user, String role, String operation) {
        this.user = user;
        this.role = role;
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    public String getOperation() {
        return operation;
    }

}