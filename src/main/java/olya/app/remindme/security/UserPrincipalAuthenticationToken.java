package olya.app.remindme.security;

import olya.app.remindme.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final User principal;

    public UserPrincipalAuthenticationToken(User principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return principal;
    }
}
