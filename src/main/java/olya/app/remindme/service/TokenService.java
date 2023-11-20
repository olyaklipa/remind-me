package olya.app.remindme.service;

import java.time.Instant;
import olya.app.remindme.model.User;

public interface TokenService {
    void createTokenEntity(String token, Instant expiresAt, String email);
    void revokeUserTokens(User user);
    void clearExpiredTokenEntities();
}
