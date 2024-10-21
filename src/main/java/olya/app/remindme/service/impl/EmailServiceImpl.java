package olya.app.remindme.service.impl;

import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.Action;
import olya.app.remindme.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${hostname}")
    private String hostName;

    @Override
    public EmailData generateEmailData(Action action, LocalDate scheduledDate, String template, String eventId) {
        EmailData data = new EmailData();
        data.setTo(action.getSubject().getUser().getEmail());
        data.setUserFirstname(action.getSubject().getUser().getFirstName());
        data.setActionTitle(action.getTitle());
        data.setSubjectName(action.getSubject().getName());
        data.setScheduledDate(scheduledDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        data.setTemplate(template);
        data.setId(eventId);
        return data;
    }

    @Override
    @Async
    public void sendHtmlEmail(EmailData emailData) {
        try {
            Context context = new Context();
            context.setVariable("userFirstname", emailData.getUserFirstname());
            context.setVariable("actionTitle", emailData.getActionTitle());
            context.setVariable("subjectName", emailData.getSubjectName());
            context.setVariable("scheduledDate", emailData.getScheduledDate());
            context.setVariable("eventId", emailData.getId());
            context.setVariable("hostName", hostName);
            String text = templateEngine.process(emailData.getTemplate(), context);
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject(emailData.getSubject());
            helper.setFrom(from);
            helper.setTo(emailData.getTo());
            helper.setText(text, true);
            mailSender.send(mailMessage);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
