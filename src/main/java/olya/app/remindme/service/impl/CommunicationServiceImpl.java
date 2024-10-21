package olya.app.remindme.service.impl;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.Action;
import olya.app.remindme.service.CommunicationService;
import olya.app.remindme.service.EmailService;
import olya.app.remindme.service.SubjectService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {
    private final EmailService emailService;

    @Override
    public void sendData(Action action, LocalDate date, String template, String eventId) {
        switch (action.getNotificationMethod()) {
            case EMAIL:
                sendEmail(action, date, template, eventId);
                break;
            case TELEGRAM:
                //send to telegram bot
                break;
            case EMAIL_AND_TELEGRAM:
                sendEmail(action, date, template, eventId);
                //send to telegram bot
        }
    }

    private void sendEmail(Action action, LocalDate date, String template, String eventId) {
        EmailData data = emailService.generateEmailData(action, date, template, eventId);
        emailService.sendHtmlEmail(data);
    }

}
