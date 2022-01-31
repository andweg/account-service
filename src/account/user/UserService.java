package account.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    public static final int MAX_FAILED_LOGIN_ATTEMPTS = 5;

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(
                userRepo
                        .findByEmailIgnoreCase(username.toLowerCase())
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        String.format("User with username - %s, not found", username)
                                )
                        )
        );
    }

}