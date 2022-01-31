package account.config;

import account.user.User;
import account.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepo;

    @Autowired
    public AuthenticationSuccessListener(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        User user =  userRepo.findByEmailIgnoreCase(event.getAuthentication().getName()).get();
        if (user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);
            userRepo.save(user);
        }
    }

}