package olya.app.remindme.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;
import olya.app.remindme.model.Subject;
import olya.app.remindme.repository.EventRepository;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ActionService actionService;

    @Override
    @Transactional
    public boolean create(Event event) {
        //to use by cron job
        return false;
    }

    @Override
    @Transactional
    public Event update(Long id, Event.Status status, boolean updateDate) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The event with id " + id + " not found"));
        event.setStatus(status);
        if (updateDate) {
            LocalDate today = LocalDate.now();
            event.setDate(today);
            Action action = event.getAction();
            actionService.updateLastExecutionDate(action, today);
        }
        return eventRepository.save(event);
    }


    @Override
    @Transactional(readOnly = true)
    public Event getById(Long userId, Long id) {
        return eventRepository.findEventByUserAndId(userId, id)
                .orElseThrow(() -> new EntityNotFoundException("The event with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getByAction(Long userId, Long actionId) {
        actionService.getById(userId, actionId);
        return eventRepository.findByActionId(actionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAll(Long userId) {
        return eventRepository.findEventsByUser(userId);
    }
}
