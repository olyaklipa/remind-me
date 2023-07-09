package olya.app.remindme.service;

import olya.app.remindme.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role add(Role role);
    Role getByName(String roleName);
}
