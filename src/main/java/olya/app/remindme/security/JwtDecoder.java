package olya.app.remindme.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.MyAuthenticationException;
import olya.app.remindme.repository.TokenRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties properties;
    private final TokenRepository tokenRepository;

    public DecodedJWT decodeAndVerify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            throw new MyAuthenticationException("JWT verification has failed");
        }

    }
}
