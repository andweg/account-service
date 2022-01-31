package account.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRetrievalService {

    private final EventRepository eventRepo;

    @Autowired
    public EventRetrievalService(EventRepository eventRepository) {
        this.eventRepo = eventRepository;
    }

    public List<Event> getAll() {
        return eventRepo.findAll();
    }

}