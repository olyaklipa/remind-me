package olya.app.remindme.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.dto.response.UserResponseDto;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import olya.app.remindme.service.TokenService;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.UserMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    //public
    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.create(userRequestDto);
        return userMapper.mapToDto(user);
    }

    //for user and admin
    @GetMapping("/my-user")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal User user) {
        return userMapper.mapToDto(user);
    }

    //for user and admin
    @PutMapping("/my-user")
    public UserResponseDto updateCurrentUser(@AuthenticationPrincipal User user, @Valid @RequestBody UserRequestDto userRequestDto) {
        User updatedUser = userService.update(user, userRequestDto);
        return userMapper.mapToDto(updatedUser);
    }

    //for user and admin
    @DeleteMapping("/my-user")
    public void deleteCurrentUser(@AuthenticationPrincipal User user) {
        userService.deactivate(user);
    }

    //for admin only
    //get user by email
    @GetMapping("/by-email")
    public List<User> getByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    //for admin only
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    //for admin only
    @GetMapping()
    public List<User> getAllUsers() {
       return userService.getAll();
    }

    //for admin only
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.getActiveUserById(id);
        return userService.update(user, userRequestDto);
    }

    //for admin only
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = userService.getActiveUserById(id);
        userService.deactivate(user);
    }

    //for admin only
    @PutMapping("/{id}/roles")
    public void updateRoles(@PathVariable Long id, @RequestBody Set<Role.RoleName> newRoles) {
        User user = userService.getActiveUserById(id);
        userService.updateRoles(user, newRoles);
    }

    //for admin only
    @DeleteMapping("/{id}/tokens")
    public void revokeTokensForUser (@PathVariable Long id) {
        User user = userService.getActiveUserById(id);
        tokenService.revokeUserTokens(user);
    }
}
