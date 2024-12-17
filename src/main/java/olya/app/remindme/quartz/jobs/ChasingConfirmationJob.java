package olya.app.remindme.quartz.jobs;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olya.app.remindme.model.Event;
import olya.app.remindme.repository.EventRepository;
import olya.app.remindme.service.CommunicationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChasingConfirmationJob implements Job {
    public static final String TEMPLATE = "chasingConfirmationEmailTemplate.html";
    private final EventRepository eventRepository;
    private final CommunicationService communicationService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("ChasingConfirmationJob execution started");
        List<Event> events = eventRepository.findByStatusAndRequiresConfirmation(Event.Status.PENDING);
        for (Event event : events) {
            String eventId = String.valueOf(event.getId());
            communicationService.sendData(event.getAction(), event.getDate(), TEMPLATE, eventId);
        }
        log.info("ChasingConfirmationJob execution ended");
    }
}