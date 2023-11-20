package olya.app.remindme.service;

import java.util.Set;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;

public interface UserService {
    User create(UserRequestDto userRequestDto);
    User update(User user);
    void deactivate(Long id);
    User getByEmail(String email);
    Set<Role> getUserRoles(Long userId);
}
