package olya.app.remindme.controller;

import olya.app.remindme.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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
}
