package account.user;

import account.user.dto.AdminAccessRequest;
import account.user.dto.AdminAccessResponse;
import account.user.dto.DeleteUserResponse;
import account.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @Autowired
    public UserAdminController(UserAdminService adminService) {
        this.userAdminService = adminService;
    }

    @GetMapping("user")
    public List<UserResponse> getUser() {
        return userAdminService.getAll();
    }

    @PutMapping("user/access")
    public AdminAccessResponse putUserAccess(@RequestBody AdminAccessRequest request) {
        return userAdminService.access(request);
    }

    @DeleteMapping("user/{email}")
    public DeleteUserResponse deleteUser(@PathVariable String email) {
        return userAdminService.delete(email);
    }

}