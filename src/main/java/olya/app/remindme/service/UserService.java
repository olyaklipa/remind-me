package olya.app.remindme.service;

import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.model.User;

public interface UserService {
    User add(UserRequestDto userRequestDto);
    User update(User user);
    void deactivate(Long id);
    User getByEmail(String email);
}
