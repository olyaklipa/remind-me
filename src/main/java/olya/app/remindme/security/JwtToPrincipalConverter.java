package olya.app.remindme.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToPrincipalConverter {
    private final UserService userService;
    private final RolesToAuthoritiesConverter rolesToAuthoritiesConverter;

    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .email(jwt.getClaim("e").asString())
                .authorities(getAuthoritiesByUserId(Long.valueOf(jwt.getSubject())))
                .build();
    }

    private List<SimpleGrantedAuthority> getAuthoritiesByUserId(Long userId) {
        Set<Role> roles= userService.getUserRoles(userId);
        return rolesToAuthoritiesConverter.convert(roles);
//        Claim claim = jwt.getClaim("a");
//        if (claim.isNull() || claim.isMissing()) {
//            return List.of();
//        }
//        return claim.asList(SimpleGrantedAuthority.class);

    }
}
