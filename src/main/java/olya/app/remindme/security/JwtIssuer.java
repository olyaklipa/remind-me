package olya.app.remindme.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(long userId, Instant expiresAt, String email) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(expiresAt)
                .withClaim("e", email)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
