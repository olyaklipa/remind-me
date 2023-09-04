package olya.app.remindme.security;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final RolesToAuthoritiesConverter rolesToAuthoritiesConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                rolesToAuthoritiesConverter.convert(user.getRoles()));
    }
}
