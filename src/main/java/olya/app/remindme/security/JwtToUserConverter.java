package olya.app.remindme.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.User;
import olya.app.remindme.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToUserConverter {
    private final UserService userService;

    public User convert(DecodedJWT jwt) {
        String email = jwt.getClaim("e").asString();
        return userService.getActiveUserByEmail(email);
    }
}
