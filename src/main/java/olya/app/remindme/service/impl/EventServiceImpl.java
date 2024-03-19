package olya.app.remindme.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Event;
import olya.app.remindme.repository.EventRepository;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.EventService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ActionService actionService;

    @Override
    public boolean create(Event event) {
        //to use by cron job
        return false;
    }

    @Override
    public Event update(Event.Status status) {
        //to use by cron job
        return null;
    }

    @Override
    public Event getById(Long userId, Long id) {
        return eventRepository.findEventByUserAndId(userId, id)
                .orElseThrow(() -> new EntityNotFoundException("The event with id " + id + " not found"));
    }

    @Override
    public List<Event> getByAction(Long userId, Long actionId) {
        actionService.getById(userId, actionId);
        return eventRepository.findEventsByAction(actionId);
    }

    @Override
    public List<Event> getAll(Long userId) {
        return eventRepository.findEventsByUser(userId);
    }
}
