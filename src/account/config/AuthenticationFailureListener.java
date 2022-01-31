package account.config;

import account.event.EventRegistrationService;
import account.user.User;
import account.user.UserRepository;
import account.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFailureListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserRepository userRepo;
    private final EventRegistrationService eventRegistrationService;

    @Autowired
    public AuthenticationFailureListener(UserRepository userRepo,
                                         EventRegistrationService eventRegistrationService) {
        this.userRepo = userRepo;
        this.eventRegistrationService = eventRegistrationService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = event.getAuthentication().getPrincipal().toString();
        Optional<User> loginTarget = userRepo.findByEmailIgnoreCase(email);
        eventRegistrationService.registerLoginFailed(email);
        if (loginTarget.isPresent()) {
            User user = loginTarget.get();
            if (!user.hasAdminRole()) {
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
                if (user.getFailedLoginAttempts() >= UserService.MAX_FAILED_LOGIN_ATTEMPTS
                        && user.isAccountNonLocked()) {
                    eventRegistrationService.registerBruteForce(email);
                    user.setAccountNonLocked(false);
                    eventRegistrationService.registerLockUser(email);
                }
                userRepo.save(user);
            }
        }
    }

}