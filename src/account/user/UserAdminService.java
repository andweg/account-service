package account.user;

import account.event.EventRegistrationService;
import account.exception.AdminLockAttempt;
import account.exception.AdminRemovalAttempt;
import account.exception.UnsupportedOperation;
import account.exception.UserDoesNotExist;
import account.user.dto.AdminAccessRequest;
import account.user.dto.AdminAccessResponse;
import account.user.dto.DeleteUserResponse;
import account.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserAdminService {

    private final UserRepository userRepo;
    private final EventRegistrationService eventRegistrationService;

    @Autowired
    public UserAdminService(UserRepository userRepo,
                            EventRegistrationService eventRegistrationService) {
        this.userRepo = userRepo;
        this.eventRegistrationService = eventRegistrationService;
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepo.findAllByOrderByIdAsc();
        List<UserResponse> output = new ArrayList<>();
        for (User user : users) {
            output.add(new UserResponse(user));
        }
        return output;
    }

    public AdminAccessResponse access(AdminAccessRequest request) {
        String email = request.getUser().toLowerCase();
        User user = getUserOrThrow(email);
        String operation = request.getOperation();
        String status;
        if (operation.equalsIgnoreCase("LOCK")) {
            lock(user);
            status = "User " + email + " locked!";
        } else if (operation.equalsIgnoreCase("UNLOCK")) {
            unlock(user);
            status = "User " + email + " unlocked!";
        } else {
            throw new UnsupportedOperation();
        }
        userRepo.save(user);
        return new AdminAccessResponse(status);
    }

    public DeleteUserResponse delete(String email) {
        User user = getUserOrThrow(email);
        if (user.hasAdminRole()) {
            throw new AdminRemovalAttempt();
        }
        userRepo.delete(user);
        eventRegistrationService.registerDeleteUser(email);
        return new DeleteUserResponse(email, "Deleted successfully!");
    }

    private void lock(User user) {
        if (user.hasAdminRole()) {
            throw new AdminLockAttempt();
        }
        user.setAccountNonLocked(false);
        eventRegistrationService.registerLockUser(
                user.getEmail()
        );
    }

    private void unlock(User user) {
        user.setAccountNonLocked(true);
        user.setFailedLoginAttempts(0);
        eventRegistrationService.registerUnlockUser(
                user.getEmail()
        );
    }

    private User getUserOrThrow(String email) {
        return userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(UserDoesNotExist::new);
    }

}