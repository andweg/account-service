package account.user.dto;

public class AdminAccessResponse {

    private final String status;

    public AdminAccessResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}