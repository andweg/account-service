package account.user;

import account.event.EventRegistrationService;
import account.exception.UserAlreadyExists;
import account.exception.UserDoesNotExist;
import account.user.dto.ChangepassRequest;
import account.user.dto.ChangepassResponse;
import account.user.dto.SignupRequest;
import account.user.dto.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserAuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserPasswordValidator passwordValidator;
    private final EventRegistrationService eventRegistrationService;

    @Autowired
    public UserAuthService(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           UserPasswordValidator passwordValidator,
                           EventRegistrationService eventRegistrationService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
        this.eventRegistrationService = eventRegistrationService;
    }

    public SignupResponse add(SignupRequest request) {
        String email = request.getEmail();
        throwIfExists(email);
        passwordValidator.validate(request.getPassword());
        User user = new User(
                request.getName(),
                request.getLastname(),
                request.getEmail().toLowerCase(),
                passwordEncoder.encode(request.getPassword())
        );
        setStartingRole(user);
        user.setAccountNonLocked(true);
        userRepo.save(user);
        eventRegistrationService.registerCreateUser(user.getEmail());
        return new SignupResponse(user);
    }

    public ChangepassResponse changePassword(ChangepassRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getUserOrThrow(email);
        String newPassword = request.getNew_password();
        passwordValidator.validateChanged(newPassword, user.getPassword());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        eventRegistrationService.registerChangePassword();
        return new ChangepassResponse(
                email,
                "The password has been updated successfully"
        );
    }

    private void setStartingRole(User user) {
        if (userRepo.findAll().isEmpty()) {
            user.getRoles().add(UserRole.ROLE_ADMINISTRATOR);
        } else {
            user.getRoles().add(UserRole.ROLE_USER);
        }
    }

    private void throwIfExists(String email) {
        if (userRepo.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExists();
        }
    }

    private User getUserOrThrow(String email) {
        return userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(UserDoesNotExist::new);
    }

}