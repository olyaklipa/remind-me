package olya.app.remindme.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.response.LoginResponse;
import olya.app.remindme.model.User;
import olya.app.remindme.security.JwtIssuer;
import olya.app.remindme.service.AuthService;
import olya.app.remindme.service.TokenService;
import olya.app.remindme.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    @Transactional
    public LoginResponse attemptLogin(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        Instant expiresAt = Instant.now().plus(Duration.of(1, ChronoUnit.DAYS));
        String token = jwtIssuer.issue(user.getId(), expiresAt, user.getEmail());
        tokenService.createTokenEntity(token, expiresAt, user.getEmail());
        return new LoginResponse(token);
    }

    @Override
    @Transactional
    public void logout(String email) {
        tokenService.revokeUserTokens(userService.getActiveUserByEmail(email));
        SecurityContextHolder.clearContext();
    }
}
