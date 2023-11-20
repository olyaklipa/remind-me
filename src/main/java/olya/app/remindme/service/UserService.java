package olya.app.remindme.service;

import java.util.List;
import java.util.Set;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;

public interface UserService {
    User create(UserRequestDto userRequestDto);
    User update(User user, UserRequestDto userRequestDto);
    void deactivate(User user);
    List<User> getByEmail(String email);
    User getActiveUserByEmail(String email);
    User getById(Long id);
    User getActiveUserById(Long id);
    List<User> getAll();
    void updateRoles(User user, Set<Role.RoleName> roleNames);

}
