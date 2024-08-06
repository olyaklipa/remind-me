package olya.app.remindme.controller;

import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.EmailData;
import olya.app.remindme.model.User;
import olya.app.remindme.service.EmailService;
import olya.app.remindme.service.SubjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final EmailService emailService;

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal User user) {
        return "If you see this, then you are logged in as user " + user.getEmail()
                + " User Id: " + user.getId() + " authorities: " + user.getAuthorities();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal User user) {
        return "If you see this, then you are ADMIN with Email: " + user.getEmail()
                + " User Id: " + user.getId() + " authorities: " + user.getAuthorities();
    }

    @PostMapping("/send-email")
    public String sendConfirmationEmail(@RequestBody EmailData emailData) {
        emailService.sendHtmlEmail(emailData);
        return "success";
    }
}
