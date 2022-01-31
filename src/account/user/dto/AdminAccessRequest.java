package account.user.dto;

import javax.validation.constraints.NotBlank;

public class AdminAccessRequest {

    @NotBlank
    private final String user;

    @NotBlank
    private final String operation;

    public AdminAccessRequest(String user, String operation) {
        this.user = user;
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public String getOperation() {
        return operation;
    }

}