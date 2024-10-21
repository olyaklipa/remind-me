package olya.app.remindme.security;

import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PublicEndpointsConfig {
    private final List<String> publicEndpoints = List.of(
            "/api/auth/login",
            "/users/registration",
            "/test",
            "/send-email",
            "/events/{id}/status"
    );
}
