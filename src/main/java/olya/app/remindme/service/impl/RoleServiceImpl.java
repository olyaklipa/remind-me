package olya.app.remindme.service.impl;

import olya.app.remindme.model.Role;
import olya.app.remindme.repository.RoleRepository;
import olya.app.remindme.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        Role.RoleName roleName = Role.RoleName.valueOf(name);
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + name + " not found"));
    }
}
