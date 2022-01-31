package account.user;

import account.event.EventRegistrationService;
import account.exception.*;
import account.user.dto.RoleUpdateRequest;
import account.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService {

    final UserRepository userRepo;
    final EventRegistrationService eventRegistrationService;

    @Autowired
    public UserRoleService(UserRepository userRepo,
                           EventRegistrationService eventRegistrationService) {
        this.userRepo = userRepo;
        this.eventRegistrationService = eventRegistrationService;
    }

    public UserResponse updateRoles(RoleUpdateRequest request) {
        User user = getUser(request.getUser());
        String roleName = request.getRole().toUpperCase();
        String operation = request.getOperation().toUpperCase();
        if (operation.equals("GRANT")) {
            grantRole(user, roleName);
        } else if (operation.equals("REMOVE")) {
            removeRole(user, roleName);
        } else {
            throw new UnsupportedOperation();
        }
        return new UserResponse(
                userRepo.save(user)
        );
    }

    private void grantRole(User user, String roleName) {
        UserRole role = getRole(roleName);
        if (user.getRoles().contains(role)) {
            throw new UserAlreadyExists();
        }
        if (role.equals(UserRole.ROLE_ADMINISTRATOR) || user.getRoles().contains(UserRole.ROLE_ADMINISTRATOR)) {
            throw new RoleGroupCombination();
        }
        user.getRoles().add(role);
        eventRegistrationService.registerGrantRole(roleName, user.getEmail());
    }

    private void removeRole(User user, String roleName) {
        UserRole role = getRole(roleName);
        if (role.equals(UserRole.ROLE_ADMINISTRATOR)) {
            throw new AdminRemovalAttempt();
        }
        if (!user.getRoles().contains(role)) {
            throw new UserDoesNotHaveRole();
        }
        if (user.getRoles().size() == 1) {
            throw new OnlyRoleRemoval();
        }
        user.getRoles().remove(role);
        eventRegistrationService.registerRemoveRole(roleName, user.getEmail());
    }

    private UserRole getRole(String roleName) {
        return Optional.of(UserRole.valueOf("ROLE_" + roleName))
                .orElseThrow(RoleDoesNotExist::new);
    }

    private User getUser(String userEmail) {
        return userRepo.findByEmailIgnoreCase(userEmail)
                .orElseThrow(UserDoesNotExist::new);
    }

}