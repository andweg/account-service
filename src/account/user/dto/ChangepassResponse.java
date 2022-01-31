package account.user.dto;

public class ChangepassResponse {

    private final String email;
    private final String status;

    public ChangepassResponse(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

}