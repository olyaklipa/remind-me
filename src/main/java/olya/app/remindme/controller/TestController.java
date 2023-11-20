package olya.app.remindme.controller;

import olya.app.remindme.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "If you see this, then you are logged in as user " + principal.getEmail()
                + " User Id: " + principal.getUserId() + " authorities: " + principal.getAuthorities();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "If you see this, then you are ADMIN with Email: " + principal.getEmail()
                + " User Id: " + principal.getUserId() + " authorities: " + principal.getAuthorities();
    }
}
