package account.user;

import account.exception.PasswordBlacklisted;
import account.exception.PasswordTooShort;
import account.exception.PasswordUnchanged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserPasswordValidator {

    private final static int MINIMUM_PASSWORD_LENGTH = 12;

    private final Set<String> blacklistedPasswords =
            Set.of("PasswordForJanuary",
                    "PasswordForFebruary",
                    "PasswordForMarch",
                    "PasswordForApril",
                    "PasswordForMay",
                    "PasswordForJune",
                    "PasswordForJuly",
                    "PasswordForAugust",
                    "PasswordForSeptember",
                    "PasswordForOctober",
                    "PasswordForNovember",
                    "PasswordForDecember"
            );

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserPasswordValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void validate(String password) {
        isPasswordTooShort(password);
        isPasswordBlacklisted(password);
    }

    public void validateChanged(String newPassword, String oldPassword) {
        isPasswordTooShort(newPassword);
        isPasswordBlacklisted(newPassword);
        isPasswordSameAsPreviousOne(newPassword, oldPassword);
    }

    public void isPasswordTooShort(String password) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new PasswordTooShort();
        }
    }

    public void isPasswordBlacklisted(String password) {
        if (blacklistedPasswords.contains(password)) {
            throw new PasswordBlacklisted();
        }
    }
    public void isPasswordSameAsPreviousOne(String newPassword, String oldPassword) {
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new PasswordUnchanged();
        }
    }

}