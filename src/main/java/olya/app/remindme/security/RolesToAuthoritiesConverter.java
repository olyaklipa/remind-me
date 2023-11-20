package olya.app.remindme.security;

import java.util.List;
import java.util.Set;
import olya.app.remindme.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RolesToAuthoritiesConverter {
    public List<SimpleGrantedAuthority> convert(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
    }
}
