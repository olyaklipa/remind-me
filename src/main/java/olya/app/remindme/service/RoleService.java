package olya.app.remindme.service;

import olya.app.remindme.model.Role;

public interface RoleService {
    Role add(Role role);
    Role getByName(String roleName);
}
