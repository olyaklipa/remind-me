package olya.app.remindme.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.exception.CustomRequestException;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.RoleService;
import olya.app.remindme.service.TokenService;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TokenService tokenService;

    @Override
    @Transactional
    public User create(UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
//        existedActiveUser(userRequestDto.getEmail());
        User user = new User();
        user.setActive(true);
        user.setRoles(Set.of(roleService.getByName("USER")));
        user = userMapper.mapToUser(user, userRequestDto);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, UserRequestDto userRequestDto) {
        validateRepeatPassword(userRequestDto.getPassword(), userRequestDto.getRepeatPassword());
        User updatedUser = userMapper.mapToUser(user, userRequestDto);
        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void deactivate(User user) {
        user.setActive(null);
        userRepository.save(user);
        tokenService.revokeUserTokens(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getActiveUserByEmail(String email) {
        return userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new EntityNotFoundException("Active user with email " + email + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getActiveUserById(Long id) {
        return userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Active user with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateRoles(User user, Set<Role.RoleName> roleNames) {
        Set<Role> newRoles = roleNames.stream()
                .map(roleName -> roleService.getByName(roleName.name()))
                .collect(Collectors.toSet());
        user.setRoles(newRoles);
        userRepository.save(user);
    }

    private void validateRepeatPassword(String password, String repeatPassword){
        if (!password.equals(repeatPassword)){
            throw new CustomRequestException("Repeat password does not match password");
        }
    }

//    private void existedActiveUser(String email) {
//        userRepository.findByEmailAndActiveTrue(email)
//                .ifPresent(user -> {throw new ExistedEntityException("The user with email " + email + " already exist");});
//    }
}
