package account.user.dto;

import javax.validation.constraints.NotBlank;

public class ChangepassRequest {

    @NotBlank
    private String new_password;

    public ChangepassRequest(String new_password) {
        this.new_password = new_password;
    }

    public ChangepassRequest() {
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}