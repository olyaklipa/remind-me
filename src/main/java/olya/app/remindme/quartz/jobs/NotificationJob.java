package olya.app.remindme.quartz.jobs;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.Action;
import olya.app.remindme.repository.ActionRepository;
import olya.app.remindme.service.CommunicationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationJob implements Job {
    public static final String TEMPLATE = "notificationEmailTemplate.html";
    private final ActionRepository actionRepository;
    private final CommunicationService communicationService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("NotificationJob execution started");
        List<Action> actions = actionRepository.findAllByActiveTrue();
        for (Action action : actions) {
            if (action.getNumOfDaysBeforeEventForAdvanceNotice() != null) {
                sendNotification(action, action.getNumOfDaysBeforeEventForAdvanceNotice());
            }
            if (action.getNumOfDaysBeforeEventForShortNotice() != null) {
                sendNotification(action, action.getNumOfDaysBeforeEventForShortNotice());
            }
        }
        log.info("NotificationJob execution ended");
    }

    private void sendNotification(Action action, int numOfDaysBeforeEvent) {
        LocalDate today = LocalDate.now();
        LocalDate lastExecutionDate = action.getLastExecutionDate() == null ? action.getStartDate() : action.getLastExecutionDate();
        LocalDate nextEventDate = lastExecutionDate.plusDays(action.getRepeatEveryNumOfDays());
        LocalDate noticeDate = nextEventDate.minusDays(numOfDaysBeforeEvent);
        if (today.isEqual(noticeDate)) {
            communicationService.sendData(action, nextEventDate, TEMPLATE, "");
        }
    }
}