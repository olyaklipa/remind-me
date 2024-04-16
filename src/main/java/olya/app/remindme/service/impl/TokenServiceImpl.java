package olya.app.remindme.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Token;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.TokenRepository;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.TokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void createTokenEntity(String token, Instant expiresAt, String email) {
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setRevoked(false);
        tokenEntity.setValidUntil(Timestamp.from(expiresAt));
        tokenEntity.setUser(userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new EntityNotFoundException("User " + email + " not found")));
        tokenRepository.save(tokenEntity);
    }

    @Override
    @Transactional
    public void revokeUserTokens(User user) {
        List<Token> userTokens = tokenRepository.findByUserIdAndRevokedFalse(user.getId());
        if (userTokens.isEmpty())
            return;
        userTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(userTokens);
    }

    @Override
    @Scheduled(cron = "0 34 19 * * SUN")
    @Transactional
    public void clearExpiredTokenEntities() {
        List<Token> expiredTokens = tokenRepository.findByValidUntilBefore(new Timestamp(System.currentTimeMillis()));
        tokenRepository.deleteAll(expiredTokens);
    }
}
