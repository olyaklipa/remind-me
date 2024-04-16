package olya.app.remindme.service;

import java.util.List;
import olya.app.remindme.model.Role;

public interface RoleService {
    Role add(Role.RoleName roleName);
    Role getByName(String roleName);
    List<Role> getAll();
}
