package olya.app.remindme.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.exception.ExistedEntityException;
import olya.app.remindme.exception.RepeatPasswordException;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.RoleService;
import olya.app.remindme.service.TokenService;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TokenService tokenService;

    @Override
    public User create(UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        existedActiveUser(userRequestDto.getEmail());
        User user = new User();
        user.setActive(true);
        user.setRoles(Set.of(roleService.getByName("USER")));
        user = userMapper.mapToUser(user, userRequestDto);
        return userRepository.save(user);
    }

    @Override
    public User update(User user, UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        User updatedUser = userMapper.mapToUser(user, userRequestDto);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deactivate(User user) {
        user.setActive(null);
        userRepository.save(user);
        tokenService.revokeUserTokens(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User " + email + " not found"));
    }

    @Override
    public User getActiveUserByEmail(String email) {
        return userRepository.findActiveUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Active user with email " + email + " not found"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public User getActiveUserById(Long id) {
        return userRepository.findActiveUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Active user with id " + id + " not found"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Set<Role> getUserRoles(Long userId) {
        return userRepository.findRolesById(userId);
    }

    @Override
    public void updateRoles(User user, Set<Role.RoleName> roleNames) {
        Set<Role> newRoles = roleNames.stream()
                .map(roleName -> roleService.getByName(roleName.name()))
                .collect(Collectors.toSet());
        user.setRoles(newRoles);
        userRepository.save(user);
    }

    private void validateRepeatPassword(String password, String repeatPassword){
        if (!password.equals(repeatPassword)){
            throw new RepeatPasswordException("Repeat password does not match password");
        }
    }

    private void existedActiveUser(String email) {
        userRepository.findActiveUserByEmail(email)
                .ifPresent(user -> {throw new ExistedEntityException("The user with email " + email + " already exist");});
    }
}
