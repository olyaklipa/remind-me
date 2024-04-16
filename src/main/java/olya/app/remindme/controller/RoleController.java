package olya.app.remindme.controller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.Role;
import olya.app.remindme.service.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    //for admin only
    @GetMapping
    List<Role> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    Role create(@RequestBody Role.RoleName newRoleName) {
        return roleService.add(newRoleName);
    }
}
