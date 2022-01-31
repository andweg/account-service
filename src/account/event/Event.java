package account.event;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;
    private EventAction action;
    private String subject;
    private String object;
    private String path;

    public Event(LocalDateTime date,
                 EventAction action,
                 String subject,
                 String object,
                 String path) {
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EventAction getAction() {
        return action;
    }

    public void setAction(EventAction action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", action=" + action +
                ", subject='" + subject + '\'' +
                ", object='" + object + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

}