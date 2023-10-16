package olya.app.remindme.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.Token;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.TokenRepository;
import olya.app.remindme.service.TokenService;
import olya.app.remindme.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final TokenRepository tokenRepository;

    public void createTokenEntity(String token, Instant expiresAt, String email) {
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setRevoked(false);
        tokenEntity.setValidUntil(Timestamp.from(expiresAt));
        tokenEntity.setUser(userService.getByEmail(email));
        tokenRepository.save(tokenEntity);
    }

    public void revokeUserTokens(User user) {
        List<Token> userTokens = tokenRepository.findByUserIdAndRevokedFalse(user.getId());
        if (userTokens.isEmpty())
            return;
        userTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(userTokens);
    }

    @Scheduled(cron = "0 34 19 * * SUN")
    public void clearExpiredTokenEntities() {
        List<Token> expiredTokens = tokenRepository.findExpiredTokens();
        tokenRepository.deleteAll(expiredTokens);
    }
}
