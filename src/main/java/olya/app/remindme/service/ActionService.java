package olya.app.remindme.service;

import java.time.LocalDate;
import java.util.List;
import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;

public interface ActionService {
    Action create(Long userId, ActionRequestDto actionRequestDto);
    Action update(Long userId, Long id, ActionRequestDto actionRequestDto);
    void updateLastExecutionDate(Action action, LocalDate date);
//    Action suspend(Long userId, Long id);
//    Action reactivate(Long userId, Long id);
    void delete(Long userId, Long id);
    Action getById(Long userId, Long id);
    List<Action>getBySubject(Long userId, Long subjectId);
    List<Action> getAll(Long userId);

}
