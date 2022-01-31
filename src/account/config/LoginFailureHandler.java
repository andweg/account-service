package account.config;

import account.event.EventRegistrationService;
import account.user.User;
import account.user.UserRepository;
import account.user.UserService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class LoginFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepo;
    private final EventRegistrationService eventRegistrationService;

    public LoginFailureHandler(UserRepository userRepo, EventRegistrationService eventRegistrationService) {
        this.userRepo = userRepo;
        this.eventRegistrationService = eventRegistrationService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        Optional<User> loginTarget = userRepo.findByEmailIgnoreCase(email);
        eventRegistrationService.registerLoginFailed(email);
        if (loginTarget.isPresent()) {
            User user = loginTarget.get();
            eventRegistrationService.registerLoginFailed(email);
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= UserService.MAX_FAILED_LOGIN_ATTEMPTS
                    && user.isAccountNonLocked()) {
                eventRegistrationService.registerBruteForce(email);
                user.setAccountNonLocked(false);
                eventRegistrationService.registerLockUser(email);
                exception = new LockedException("Your account has been locked due to "
                        + UserService.MAX_FAILED_LOGIN_ATTEMPTS
                        + "consecutive failed login attempts. Please contact the administrator."
                );
            }
            userRepo.save(user);
        }
        super.onAuthenticationFailure(request, response, exception);

    }
}