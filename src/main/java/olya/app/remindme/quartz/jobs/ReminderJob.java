package olya.app.remindme.quartz.jobs;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;
import olya.app.remindme.repository.ActionRepository;
import olya.app.remindme.repository.EventRepository;
import olya.app.remindme.service.CommunicationService;
import olya.app.remindme.service.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderJob implements Job {
    public static final String TEMPLATE = "reminderEmailTemplate.html";
    private final ActionRepository actionRepository;
    private final CommunicationService communicationService;
    private final EventRepository eventRepository;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("ReminderJob execution started");
        LocalDate today = LocalDate.now();
        List<Action> actions = actionRepository.findAllByActiveTrue();
        for (Action action : actions) {
            LocalDate lastExecutionDate = (action.getLastExecutionDate() == null)
                    ? action.getStartDate()
                    : action.getLastExecutionDate();
            LocalDate nextEventDate = lastExecutionDate.plusDays(action.getRepeatEveryNumOfDays());
            if (today.isEqual(nextEventDate)) {
                createEvent(action);
                updateAction (action);
                communicationService.sendData(action, today, TEMPLATE, "");
            }
        }
        log.info("ReminderJob execution ended");
    }


    private void createEvent(Action action) {
        Event event = new Event();
        event.setAction(action);
        event.setDate(LocalDate.now());
        event.setStatus(Event.Status.NA);
        eventRepository.save(event);
    }

    private void updateAction (Action action) {
        action.setLastExecutionDate(LocalDate.now());
        if (action.getNumOfRepeats() != null) {
            int updatedRepeatsCount = action.getRepeatsCount() + 1;
            if (action.getNumOfRepeats() == updatedRepeatsCount && !action.isRequiresConfirmation()) {
                action.setActive(false);
            }
            action.setRepeatsCount(updatedRepeatsCount);
        }
        actionRepository.save(action);
    }
}
