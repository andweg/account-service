package account.user.dto;

public class DeleteUserResponse {

    private final String user;
    private final String status;

    public DeleteUserResponse(String user, String status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

}