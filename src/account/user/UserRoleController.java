package account.user;

import account.user.dto.RoleUpdateRequest;
import account.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/")
public class UserRoleController {

    final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PutMapping("user/role")
    public UserResponse putUserRole(@RequestBody RoleUpdateRequest request) {
        return userRoleService.updateRoles(request);
    }

}