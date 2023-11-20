package olya.app.remindme.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.LoginRequest;
import olya.app.remindme.dto.response.LoginResponse;
import olya.app.remindme.model.User;
import olya.app.remindme.service.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

// first admin user gets populated automatically. Its password is "adminPass".
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/logout")
    public void logout(@AuthenticationPrincipal User user) {
        authService.logout(user.getEmail());

    }
}
