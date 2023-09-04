package olya.app.remindme.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.LoginRequest;
import olya.app.remindme.model.LoginResponse;
import olya.app.remindme.security.JwtIssuer;
import olya.app.remindme.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

// first admin user gets populated automatically. Its password is "admin".
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.attemptLogin(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
