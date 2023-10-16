package olya.app.remindme.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.Token;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.TokenRepository;
import olya.app.remindme.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //revokeUserTokens(userService.getByEmail(email));
    }

    private void revokeUserTokens(User user) {
        List<Token> userTokens = tokenRepository.findByUserIdAndRevokedFalse(user.getId());
        if (userTokens.isEmpty())
            return;
        userTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(userTokens);
    }
}
