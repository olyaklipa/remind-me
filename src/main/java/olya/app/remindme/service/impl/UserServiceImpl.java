package olya.app.remindme.service.impl;

import java.util.Set;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.RoleService;
import olya.app.remindme.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User add(UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        User user = new User();
        user.setActive(true);
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPassword(userRequestDto.getPassword());
        user.setRoles(Set.of(roleService.getByName("USER")));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deactivate(Long id) {

    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    public void validateRepeatPassword(String password, String repeatPassword){
        if (!password.equals(repeatPassword)){
            throw new RuntimeException("repeat password does not match password");
        }
    }
}
