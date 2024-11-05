package olya.app.remindme.service;

import java.util.List;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;

public interface EventService {
    Event create(Action action);
    Event update(Long id, Event.Status status, boolean updateDate);
    Event getById(Long userId, Long id);
    List<Event> getByAction(Long userId, Long actionId);
    List<Event> getAll(Long userId);

}
