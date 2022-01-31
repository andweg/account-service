package account.user;

import account.user.dto.ChangepassRequest;
import account.user.dto.ChangepassResponse;
import account.user.dto.SignupRequest;
import account.user.dto.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth/")
public class UserAuthController {

    private final UserAuthService authService;

    @Autowired
    public UserAuthController(UserAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return authService.add(request);
    }

    @PostMapping("changepass")
    public ChangepassResponse changepass(@Valid @RequestBody ChangepassRequest request) {
        return authService.changePassword(request);
    }

}