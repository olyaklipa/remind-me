package olya.app.remindme.service;

import java.time.LocalDate;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.Action;

public interface EmailService {
    EmailData generateEmailData(Action action, LocalDate scheduledDate, String template, String eventId);
    void sendHtmlEmail(EmailData emailData);
}
