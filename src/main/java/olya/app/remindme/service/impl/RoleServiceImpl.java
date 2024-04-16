package olya.app.remindme.service.impl;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Role;
import olya.app.remindme.repository.RoleRepository;
import olya.app.remindme.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Role add(Role.RoleName roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getByName(String name) {
        Role.RoleName roleName = Role.RoleName.valueOf(name);
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role " + name + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
