package account.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/security/")
public class EventController {

    private final EventRetrievalService eventRetrievalService;

    @Autowired
    public EventController(EventRetrievalService securityService) {
        this.eventRetrievalService = securityService;
    }

    @GetMapping("events")
    public List<Event> getEvents() {
        return eventRetrievalService.getAll();
    }

}