package olya.app.remindme.service.impl;

import java.util.List;
import java.util.Set;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.exception.UserNotFoundException;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.RoleService;
import olya.app.remindme.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        User user = new User();
        user.setActive(true);
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
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
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));
    }

    public Set<Role> getUserRoles(Long userId) {
        return userRepository.findRolesById(userId);
    }

    private void validateRepeatPassword(String password, String repeatPassword){
        if (!password.equals(repeatPassword)){
            throw new RuntimeException("repeat password does not match password");
        }
    }
}
