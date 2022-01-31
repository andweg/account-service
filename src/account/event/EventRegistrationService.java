package account.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class EventRegistrationService {

    private final EventRepository eventRepo;

    @Autowired
    public EventRegistrationService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public void registerCreateUser(String email) {
        register(
                EventAction.CREATE_USER,
                "Anonymous",
                email
        );
    }

    public void registerChangePassword() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        register(
                EventAction.CHANGE_PASSWORD,
                username,
                username
        );
    }

    public void registerAccessDenied(String username) {
        register(
                EventAction.ACCESS_DENIED,
                username
        );
    }

    public void registerLoginFailed(String email) {
        register(
                EventAction.LOGIN_FAILED,
                email
        );
    }

    public void registerGrantRole(String role, String email) {
        register(
                EventAction.GRANT_ROLE,
                SecurityContextHolder.getContext().getAuthentication().getName(),
                "Grant role " + role + " to " + email
        );
    }

    public void registerRemoveRole(String role, String email) {
        register(
                EventAction.REMOVE_ROLE,
                SecurityContextHolder.getContext().getAuthentication().getName(),
                "Remove role " + role + " from " + email
        );
    }

    public void registerLockUser(String email) {
        register(
                EventAction.LOCK_USER,
                email,
                "Lock user " + email
        );
    }

    public void registerUnlockUser(String email) {
        register(
                EventAction.UNLOCK_USER,
                SecurityContextHolder.getContext().getAuthentication().getName(),
                "Unlock user " + email
        );
    }

    public void registerDeleteUser(String email) {
        register(
                EventAction.DELETE_USER,
                SecurityContextHolder.getContext().getAuthentication().getName(),
                email
        );
    }

    public void registerBruteForce(String email) {
        register(
                EventAction.BRUTE_FORCE,
                email
        );
    }

    private void register(EventAction action,
                          String subject) {
        register(
                action,
                subject,
                ServletUriComponentsBuilder.fromCurrentRequest().build().getPath()
        );
    }

    private void register(EventAction action,
                          String subject,
                          String object) {
        register(
                action,
                subject,
                object,
                ServletUriComponentsBuilder.fromCurrentRequest().build().getPath()
        );
    }

    private void register(EventAction action,
                          String subject,
                          String object,
                          String path) {
        Event event = new Event(
                LocalDateTime.now(),
                action,
                subject,
                object,
                path
        );
        eventRepo.save(event);
        System.out.println("Security event: " + event);
    }

}