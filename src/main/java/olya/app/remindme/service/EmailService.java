package olya.app.remindme.service;

import olya.app.remindme.dto.EmailData;

public interface EmailService {
    void sendHtmlEmail(EmailData emailData);
}
