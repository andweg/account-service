package account.config;

import account.user.User;
import account.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepo;

    @Autowired
    public LoginSuccessHandler(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        User user =  userRepo.findByEmailIgnoreCase(authentication.getName()).get();
        if (user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);
            userRepo.save(user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}